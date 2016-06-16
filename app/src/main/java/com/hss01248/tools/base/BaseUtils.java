package com.hss01248.tools.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class BaseUtils {

    private static BaseApplication mContext;
    private static Thread	mMainThread;
    private static long		mMainTreadId;
    private static Looper mMainLooper;
    private static Handler mHandler;

    public static void init(BaseApplication context){
        mContext = context;
        mMainThread = Thread.currentThread();
        mMainTreadId = Process.myTid();
        mMainLooper = context.getMainLooper();
        mHandler = new Handler();
    }

    public static BaseApplication getContext(){
        return mContext;
    }

    /**得到主线程id*/
    public static long getMainThreadid() {
        return mMainTreadId;
    }

    /**得到主线程Handler*/
    public static Handler getMainThreadHandler() {
        return mHandler;
    }

    /**安全的执行一个任务*/
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();

        if (curThreadId == mMainTreadId) {// 如果当前线程是主线程
            task.run();
        } else {// 如果当前线程不是主线程
            mHandler.post(task);
        }

    }

    /**延迟执行任务*/
    public static void postTaskDelay(Runnable task, int delayMillis) {
        mHandler.postDelayed(task, delayMillis);

    }

}
