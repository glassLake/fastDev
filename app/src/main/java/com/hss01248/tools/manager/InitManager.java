package com.hss01248.tools.manager;

import com.hss01248.tools.base.BaseApplication;
import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.pack.image.MyImageUtils;
import com.hss01248.tools.pack.thread.ThreadPoolFactory;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class InitManager {

    public static void init(final BaseApplication context){

        initFirstInMainThread(context);


        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                initInOtherThread(context);
            }
        });





    }

    /**
     * 在子线程初始化
     * @param context
     */
    private static void initInOtherThread(BaseApplication context) {
        MyImageUtils.initFresco(100);
    }

    /**
     * 在主线程初始化
     * @param context
     */
    private static void initFirstInMainThread(BaseApplication context) {
        BaseUtils.init(context);
        StateManager.init();
        initLogger();
        JActivityManager.getInstance().registerActivityCallbacks();

    }

    private static void initLogger() {
        if (StateManager.isDebugMode){
            Logger.init("sntools")         // default PRETTYLOGGER or use just init()
                    .methodCount(4)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(LogLevel.FULL);      // default LogLevel.FULL
        }else {
            Logger.init("sntools")         // default PRETTYLOGGER or use just init()
                    .logLevel(LogLevel.NONE)  ;      // default LogLevel.FULL
        }



    }
}
