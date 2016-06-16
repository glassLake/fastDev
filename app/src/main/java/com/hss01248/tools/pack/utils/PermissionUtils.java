package com.hss01248.tools.pack.utils;

import android.Manifest;
import android.content.Intent;
import android.os.Build;

import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.manager.JActivityManager;
import com.hss01248.tools.pack.toast.MyToast;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class PermissionUtils {

    private static void askPermission(final Runnable runnable,String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            Acp.getInstance(BaseUtils.getContext()).request(new AcpOptions.Builder()
                            .setPermissions(permission)
//                .setDeniedMessage()
//                .setDeniedCloseBtn()
//                .setDeniedSettingBtn()
//                .setRationalMessage()
//                .setRationalBtn()
                            .build(),
                    new AcpListener() {
                        @Override
                        public void onGranted() {
                            runnable.run();
                        }

                        @Override
                        public void onDenied(List<String> permissions) {
                            MyToast.showFailToast("权限已经被拒绝");
                        }
                    });
        } else {
            // Pre-Marshmallow
            runnable.run();
        }
    }

    /**
     * group:android.permission-group.CALENDAR
     permission:android.permission.READ_CALENDAR
     permission:android.permission.WRITE_CALENDAR
     * @param runnable
     */
    public static void askCalendar(final Runnable runnable){
        askPermission(runnable, Manifest.permission.READ_CALENDAR);
    }

    /**
     * group:android.permission-group.CAMERA
     permission:android.permission.CAMERA
     * @param runnable
     */
    public static void askCamera(final Runnable runnable){
        askPermission(runnable, Manifest.permission.CAMERA);
    }


    /**
     * group:android.permission-group.STORAGE
     permission:android.permission.READ_EXTERNAL_STORAGE
     permission:android.permission.WRITE_EXTERNAL_STORAGE
     * @param runnable
     */
    public static void askExternalStorage(final Runnable runnable){
        askPermission(runnable, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * group:android.permission-group.PHONE
     *
     permission:android.permission.READ_CALL_LOG
     permission:android.permission.READ_PHONE_STATE
     permission:android.permission.CALL_PHONE
     permission:android.permission.WRITE_CALL_LOG
     permission:android.permission.USE_SIP
     permission:android.permission.PROCESS_OUTGOING_CALLS
     permission:com.android.voicemail.permission.ADD_VOICEMAIL
     * @param runnable
     */
    public static void askPhone(final Runnable runnable){
        askPermission(runnable, Manifest.permission.READ_PHONE_STATE);
    }

    /**
     * group:android.permission-group.SMS
     *
     permission:android.permission.READ_SMS
     permission:android.permission.RECEIVE_WAP_PUSH
     permission:android.permission.RECEIVE_MMS
     permission:android.permission.RECEIVE_SMS
     permission:android.permission.SEND_SMS
     permission:android.permission.READ_CELL_BROADCASTS
     * @param runnable
     */
    public static void askSms(final Runnable runnable){
        askPermission(runnable, Manifest.permission.SEND_SMS);
    }


    /**
     * group:android.permission-group.LOCATION
     permission:android.permission.ACCESS_FINE_LOCATION
     permission:android.permission.ACCESS_COARSE_LOCATION
     * @param runnable
     */
    public static void askLocationInfo(final Runnable runnable){
        askPermission(runnable, Manifest.permission.ACCESS_COARSE_LOCATION);
    }


    /**
     * group:android.permission-group.MICROPHONE
     permission:android.permission.RECORD_AUDIO
     * @param runnable
     */
    public static void askRecord(final Runnable runnable){
        askPermission(runnable, Manifest.permission.RECORD_AUDIO);
    }


    /**
     * group:android.permission-group.SENSORS
     permission:android.permission.BODY_SENSORS
     * @param runnable
     */
    public static void askSensors(final Runnable runnable){
        askPermission(runnable, Manifest.permission.BODY_SENSORS);
    }

    /**
     * group:android.permission-group.CONTACTS
     permission:android.permission.WRITE_CONTACTS
     permission:android.permission.GET_ACCOUNTS
     permission:android.permission.READ_CONTACTS
     * @param runnable
     */
    public static void askContacts(final Runnable runnable){
        askPermission(runnable, Manifest.permission.READ_CONTACTS);
    }

    public static void toAppManagerActivity(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
        if (JActivityManager.getInstance().currentActivity()!= null){
            JActivityManager.getInstance().currentActivity().startActivity(intent);
        }

    }


}
