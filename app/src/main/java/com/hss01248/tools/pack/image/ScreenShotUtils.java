package com.hss01248.tools.pack.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class ScreenShotUtils {

    /**

     * 截屏：通过读取linux帧缓存的方法
     *
     * 记得在AndroidManifest.xml加上两行权限：

     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

     <uses-permission android:name="android.permission.READ_FRAME_BUFFER" />

     还有需要注意的是，手机需要root权限

     * @param activity

     * @return

     */

    public static Bitmap captureScreen(Activity activity) {

        // 获取屏幕大小：

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager WM = (WindowManager) activity

                .getSystemService(Context.WINDOW_SERVICE);

        Display display = WM.getDefaultDisplay();

        display.getMetrics(metrics);

        int height = metrics.heightPixels; // 屏幕高

        int width = metrics.widthPixels; // 屏幕的宽

        // 获取显示方式

        int pixelformat = display.getPixelFormat();

        PixelFormat localPixelFormat1 = new PixelFormat();

        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);

        int deepth = localPixelFormat1.bytesPerPixel;// 位深

        byte[] piex = new byte[height * width * deepth];

        try {

            Runtime.getRuntime().exec(

                    new String[] { "/system/bin/su", "-c",

                            "chmod 777 /dev/graphics/fb0" });

        } catch (IOException e) {

            e.printStackTrace();

        }

        try {

            // 获取fb0数据输入流

            InputStream stream = new FileInputStream(new File(

                    "/dev/graphics/fb0"));

            DataInputStream dStream = new DataInputStream(stream);

            dStream.readFully(piex);

        } catch (Exception e) {

            e.printStackTrace();

        }

        // 保存图片

        int[] colors = new int[height * width];

        for (int m = 0; m < colors.length; m++) {

            int r = (piex[m * 4] & 0xFF);

            int g = (piex[m * 4 + 1] & 0xFF);

            int b = (piex[m * 4 + 2] & 0xFF);

            int a = (piex[m * 4 + 3] & 0xFF);

            colors[m] = (a << 24) + (r << 16) + (g << 8) + b;

        }

        // piex生成Bitmap

        Bitmap bitmap = Bitmap.createBitmap(colors, width, height,

                Bitmap.Config.ARGB_8888);

        return bitmap;

    }
}
