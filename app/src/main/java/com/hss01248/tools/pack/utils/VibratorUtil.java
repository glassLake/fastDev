package com.hss01248.tools.pack.utils;

import android.app.Service;
import android.os.Vibrator;

import com.hss01248.tools.base.BaseUtils;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class VibratorUtil {
    /**
     *
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */

    public static void Vibrate(long milliseconds) {
        Vibrator vib = (Vibrator) BaseUtils.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null &&vib.hasVibrator())
        vib.vibrate(milliseconds);
    }

    /**
     *
     * @param pattern  pattern的形式为new long[]{arg1,arg2,arg3,arg4......},
     * 其中以两个一组的如arg1 和arg2为一组、arg3和arg4为一组，
     * 每一组的前一个代表等待多少毫 秒启动vibrator，后一个代表vibrator持续多少毫秒停止，
     * 之后往复即 可。Repeat表示重复次数，当其为-1时，表示不重复只以pattern的方 式运行一次
     * @param isRepeat
     */
    public static void Vibrate(long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) BaseUtils.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null &&vib.hasVibrator())
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    public static void cancle(){
        Vibrator vib = (Vibrator) BaseUtils.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null &&vib.hasVibrator())
            vib.cancel();
    }
}
