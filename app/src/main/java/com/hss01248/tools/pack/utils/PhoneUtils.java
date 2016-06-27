package com.hss01248.tools.pack.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.hss01248.tools.base.BaseUtils;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class PhoneUtils {

    public static  int getPhoneWidth(){
        WindowManager wm1 = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm1.getDefaultDisplay().getWidth();

    }

    public static  int getPhoneHeight(){
        WindowManager wm1 = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm1.getDefaultDisplay().getHeight();

    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    public int getStateHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     2  * 获取屏幕宽高
     3  *
     4  * @param activity
     5  * @return int[0] 宽，int[1]高
     6 */
    public int[] getScreenWidthAndSizeInPx(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int[] size = new int[2];
        size[0] = displayMetrics.widthPixels;
        size[1] = displayMetrics.heightPixels;
        return size;
    }


    /**
     * 检查是否有虚拟按键
     * @param context
     * @return
     */
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.w("jj", e);
        }

        return hasNavigationBar;

    }
    //获取NavigationBar的高度：

    /**
     * 获取虚拟按键栏的高度
     * @param context
     * @return
     */
    private static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }
}
