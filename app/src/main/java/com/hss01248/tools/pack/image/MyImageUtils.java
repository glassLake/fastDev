package com.hss01248.tools.pack.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import com.commit451.nativestackblur.NativeStackBlur;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.manager.StateManager;
import com.hss01248.tools.pack.image.fresco.FrescoImageloadHelper;
import com.hss01248.tools.pack.image.fresco.FrescoUtils;
import com.hss01248.tools.pack.utils.ViewUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
public class MyImageUtils {

    public static int articleCover = ViewUtils.dip2Px(80);
    public static int avatar256 = ViewUtils.dip2Px(128);
    public static int avatar128 = ViewUtils.dip2Px(64);
    public static int tucaoCoverHeight = ViewUtils.dip2Px(60);
    public static int tucaoCoverWidth = articleCover;
    public static int audioCover = articleCover;

    public static final int TYPE_ARTICLR_COVER = 1;



    public static String getUrl(String url,boolean isQiniu){
        String newUrl = url;
        // Logger.e("进入时url:"+ newUrl);
        if (!newUrl.contains("http:") && !newUrl.contains("https:")){

            if (isQiniu){
               // newUrl = Url.GET_QINIU_URL+ newUrl ;
            }else {
                if (url.startsWith("/")){
                   // newUrl= Url.HTTP_HOST_WITCHOUT_SLASH + url;
                }else {
                   // newUrl= Url.HTTP_HOST_WITCH_SLASH + url;
                }
            }

        }

        return newUrl;

    }

    public static String getOriginUrl(String url,boolean isQiniu){
       if (isQiniu){
           return QiniuUtils.getOriginUrl(url,StateManager.isWWW);
       }else {
           return appendMyHost(url);
       }

    }


    public static String getPicUrl(String url, int width, int height, boolean isQiNiu) {
        String newUrl = url;
         Logger.e("进入时url:"+ newUrl);

        if (isQiNiu){
            newUrl = QiniuUtils.getSamllImage(newUrl,width,height,StateManager.isWWW);
        }else {
            newUrl = appendMyHost(newUrl);
        }

        Logger.e("处理后url:"+ newUrl);

        return newUrl;
    }

    public static String appendMyHost(String url){
        String newUrl = url;
       // Logger.e("进入时url:"+ newUrl);
        if (!newUrl.contains("http:") && !newUrl.contains("https:")){
            if (url.startsWith("/")){
               // newUrl= Url.HTTP_HOST_WITCHOUT_SLASH + url;
            }else {
              //  newUrl= Url.HTTP_HOST_WITCH_SLASH + url;
            }
        }

        return newUrl;

    }


    // 七牛的高斯模糊
    public static  String QiNiuBoxBlurFilter(String url){
        String newUrl="";
        if (TextUtils.isEmpty(url)){
            return newUrl;
        }

            newUrl=QiniuUtils.highBlur(url, StateManager.isWWW);



        return  newUrl;
    }


    /**
     * 高斯模糊操作
     * @param sentBitmap
     * @param radius
     * @param canReuseInBitmap
     * @return
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

// 高斯模糊
    /**
     * 水平方向模糊度
     */
    private static float hRadius = 10;
    /**
     * 竖直方向模糊度
     */
    private static float vRadius = 10;
    /**
     * 模糊迭代度
     */
    private static int iterations = 7;

    public static Bitmap BoxBlurFilter(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < iterations; i++) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
        blurFractional(inPixels, outPixels, width, height, hRadius);
        blurFractional(outPixels, inPixels, height, width, vRadius);
        bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return bitmap;
    }

    public static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];

        for (int i = 0; i < 256 * tableSize; i++)
            divide[i] = i / tableSize;
        int inIndex = 0;
        for (int y = 0; y < height; y++) {int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

                int i1 = x + r + 1;
                if (i1 > widthMinus1) i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0) i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    public static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;

            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];

                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;r1 *= f;g1 *= f;b1 *= f;
                out[outIndex]= (a1 << 24)| (r1 << 16)| (g1 << 8)| b1;
                outIndex+= height;
            }
            out[outIndex] = in[width - 1];
            inIndex+= width;
        }
    }

    public static int clamp(int x,int a,int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }





    /*特别注意：Fresco 不支持 相对路径的URI. 所有的URI都必须是绝对路径，并且带上该URI的scheme。

如下：

类型	Scheme	示例
远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
本地文件	file://	FileInputStream
Content provider	content://	ContentResolver
asset目录下的资源	asset://	AssetManager
res目录下的资源	res://	Resources.openRawResource
res 示例:

Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);*/
    public static void showRes(MyImageView imageView,int resId){
        Uri uri = Uri.parse("res://com.dd.uu/" + resId);
        imageView.setImageURI(uri);
    }


    public static void getBitmapByFresco(String url,BaseBitmapDataSubscriber dataSubscriber){
        if (TextUtils.isEmpty(url)){
            return;
        }
        FrescoImageloadHelper.loadOriginalBitmap(url, BaseUtils.getContext(), dataSubscriber);
    }


    public static void initFresco(int sizeInM){
       FrescoUtils.init(BaseUtils.getContext(),sizeInM);
    }


    private Bitmap fastBlur(Bitmap bkg, int radius) {

      Bitmap smallBitmap =   Bitmap.createScaledBitmap(bkg,bkg.getWidth()/3,bkg.getHeight()/3,true);

      return   NativeStackBlur.process(smallBitmap, radius);

      /*  float scaleFactor = 8;

        Bitmap overlay = Bitmap.createBitmap((int)(view.getMeasuredWidth()/scaleFactor),
                (int)(view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
       return NativeStackBlur.process(overlay, radius);*/
       // view.setBackground(new BitmapDrawable(getResources(), overlay));


    }






}
