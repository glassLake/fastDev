package com.hss01248.tools.manager;

/**
 * Created by Mr.Jude on 2015/2/12.
 * 管理Activity的类
 */

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.hss01248.tools.base.BaseUtils;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;

public class JActivityManager {
    private static LinkedList<Activity> activityStack;
    private static JActivityManager instance;

    public boolean isOnScreen() {
        return isOnScreen;
    }

    private  boolean isOnScreen;

    private JActivityManager() {
    }

    public static JActivityManager getInstance() {
        if (instance == null) {
            instance = new JActivityManager();
        }
        return instance;
    }

    /**
     * 获得当前栈顶Activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null && !activityStack.isEmpty())
            activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    public void setIsOnScreen(boolean isOnScreen){
       this.isOnScreen = isOnScreen;
    }

    /**
     * 将当前Activity推入栈中
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 将Activity退出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 主动退出Activity
     *
     * @param activity
     */
    public void closeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }


    public void closeAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            closeActivity(activity);
        }
    }

    /**
     * close a specific activity by its complete name.
     *
     * @param name For example: com.jude.utils.Activity_B
     */
    public void closeActivityByName(String name) {
        int index = activityStack.size() - 1;

        while (true) {
            Activity activity = activityStack.get(index);

            if (null == activity) {
                break;
            }

            String activityName = activity.getComponentName().getClassName();
            if (!TextUtils.equals(name, activityName)) {
                index--;
                if (index < 0) {//avoid index out of bound.
                    break;
                }
                continue;
            }
            popActivity(activity);
            closeActivity(activity);
            break;
        }
    }

    /**
     * 获得当前ACTIVITY 名字
     */
    public String getCurrentActivityName() {
        Activity activity = currentActivity();
        String name = "";
        if (activity != null) {
            name = activity.getComponentName().getClassName().toString();
        }
        return name;
    }

    public void registerActivityCallbacks() {
        BaseUtils.getContext().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                JActivityManager.getInstance().pushActivity(activity);
                Logger.e("onCreated:"+ activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

                Logger.e("onStarted:"+ activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                JActivityManager.getInstance().setIsOnScreen(true);
                Logger.e("onResumed:"+ activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.e("onPaused:"+ activity);
                JActivityManager.getInstance().setIsOnScreen(false);

            }

            @Override
            public void onActivityStopped(Activity activity) {

                Logger.e("onStopped:"+ activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.e("onSaveInstanceState:"+ activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                JActivityManager.getInstance().popActivity( activity);
                Logger.e("onDestroyed:"+ activity);
            }

        });
    }

}

