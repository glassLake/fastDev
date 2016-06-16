package com.hss01248.tools.pack.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hss01248.tools.base.BaseUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class ViewUtils {

    /**
     * dip-->px
     */
    public static int dip2Px(int dip) {
        // px/dip = density;
        float density = BaseUtils.getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    /**
     * px-->dip
     */
    public static int px2Dip(int px) {
        // px/dip = density;
        float density = BaseUtils.getContext().getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseUtils.getContext().getResources().getDisplayMetrics());
    }

    public static int px2sp( float pxValue) {
        final float fontScale = BaseUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     * 必须要已经有父view时再调用
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        if (view.getParent() == null){
            return;
        }

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }


    /**
     * 需要在setContentview之后再调用
     * @param activity
     */
    public static void setStatusBarColor(Activity activity,int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity,true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(colorId);
        }
    }


    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity,boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static  int getPhoneWidth(){
        WindowManager wm1 = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm1.getDefaultDisplay().getWidth();

    }

    public static  int getPhoneHeight(){
        WindowManager wm1 = (WindowManager) BaseUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm1.getDefaultDisplay().getHeight();

    }
}
