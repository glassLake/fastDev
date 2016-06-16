package com.hss01248.tools.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hss01248.tools.manager.InitManager;


/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        InitManager.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
