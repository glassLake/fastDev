package com.hss01248.tools.pack.toast;

import com.hss01248.mytoast.ToastUtils;
import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.manager.StateManager;


/**
 * Created by hss01248 on 11/25/2015.
 */
public class MyToast {

    public static void showToast(String text) {

       ToastUtils.showToast(BaseUtils.getContext(),text,false,BaseUtils.getMainThreadHandler());

    }

    public void cancelToast() {
       ToastUtils.cancelToast();
    }

    public static void showDebugToast(final String text) {

        ToastUtils.showDebugToast(BaseUtils.getContext(),text,StateManager.isDebugMode,BaseUtils.getMainThreadHandler());


    }

    public static void showLongToast(final String text) {

       ToastUtils.showLongToast(BaseUtils.getContext(),text,BaseUtils.getMainThreadHandler());

    }




    public static void showSuccessToast(String text){
        ToastUtils.showSuccessToast(BaseUtils.getContext(),text,BaseUtils.getMainThreadHandler());
    }

    public static void showFailToast(String text){
       ToastUtils.showFailToast(BaseUtils.getContext(),text,BaseUtils.getMainThreadHandler());
    }
}
