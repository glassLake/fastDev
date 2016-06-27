package com.hss01248.tools.pack.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;

import com.hss01248.tools.base.BaseUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 * 涉及到的6.0运行时权限均已添加询问及确认和拒绝的处理
 */
public class IntentUtils {

    /*
     //叫出拨号程序
    Uri uri = Uri.parse("tel:0800000123");
    Intent it = new Intent(Intent.ACTION_DIAL, uri);
    startActivity(it);
    //直接打电话出去
    Uri uri = Uri.parse("tel:0800000123");
    Intent it = new Intent(Intent.ACTION_CALL, uri);
    startActivity(it);  */


    /**
     * 需要权限<uses-permission id="android.permission.CALL_PHONE" />
     * @param mobile
     */
    public static void sysCall(final String mobile){
      PermissionUtils.askPhone(new PermissionUtils.PermissionListener() {
          @Override
          public void onGranted() {
              Uri uri = Uri.parse("tel:"+ mobile);
              Intent it = new Intent(Intent.ACTION_CALL, uri);
             /* Intent intent = new Intent();
              intent.setAction("android.intent.action.CALL");
              intent.setData(Uri.parse("tel:"+ mobile));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可*/
              it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              BaseUtils.getContext().startActivity(it);
          }

          @Override
          public void onDenied(List<String> permissions) {

          }
      });

    }

    /**
     * 调出拨号界面，无需权限
     */
    public static void sysDial(final String mobile){
        Uri uri = Uri.parse("tel:"+mobile);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(it);
    }

    /**
     * <uses-permissionandroid:name="android.permission.SEND_SMS" />
     *
     * @param mobile
     * @param content
     */
    public static void sysSendSms(final String mobile, final String content){
        PermissionUtils.askSms(new PermissionUtils.PermissionListener() {
            @Override
            public void onGranted() {
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> texts = smsManager.divideMessage(content);//拆分短信,短信字数太多了的时候要分几次发
                for(String text : texts){
                    smsManager.sendTextMessage(mobile, null, text, null, null);//发送短信,mobile是对方手机号
                }
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });

    }

    /**
     * 调出安装的浏览器来加载
     * @param url
     */
    public static void sysShowUrl(String url){
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(it);
    }

    public static void sysIntallApk( File apkFile){
        if (apkFile.isFile()) {
            String fileName = apkFile.getName();
            String postfix = fileName.substring(fileName.length() - 4, fileName.length());
            if (postfix.toLowerCase().equals(".apk")) {
                String cmd = "chmod 755 " + apkFile.getAbsolutePath();
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    Logger.e( e.getLocalizedMessage());
                }
                Uri uri = Uri.fromFile(apkFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                BaseUtils.getContext().startActivity(intent);
            }
        } else if (apkFile.isDirectory()) {
            File[] files = apkFile.listFiles();
            int fileCount = files.length;
            for (int i = 0; i < fileCount; i++) {
                sysIntallApk( files[i]);
            }
        }
    }

    public static void sysUnistallApk(String strPackageName){
        Uri uri = Uri.fromParts("package", strPackageName, null);
        Intent it = new Intent(Intent.ACTION_DELETE, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(it);
    }

    /**
     * 寻找某个应用
     * @param strPackageName
     */
    public static void sysMarketSearch(String strPackageName){
        Uri uri = Uri.parse("market://search?q=pname:"+ strPackageName);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(it);
    }

    /**
     * //显示某个应用的相关信息
     * 如果有安装应用市场，就去应用市场，如果没有，就通过浏览器跳到应用宝的页面
     */
    public static void sysMarketDetail(String strPackageName){

       /* Uri uri = Uri.parse("market://details?id=app_id");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);*/

        if (hasAnyMarketInstalled(BaseUtils.getContext())){
            String str = "market://details?id=" + strPackageName;
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setData(Uri.parse(str));
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtils.getContext().startActivity(localIntent);
        }else {
            goWangdoujia(strPackageName);
        }
    }


    private static boolean hasAnyMarketInstalled(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0 != list.size();
    }

    public static void sysPlayMediaFile(String filePath){
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setType(FileUtils.getMimeType(file));
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(it);
       /* Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);*/
    }

    /**
     * 分享功能
     * 分享功能不只是Intent.EXTRA_TEXT，还可以 EXTRA_EMAIL, EXTRA_CC, EXTRA_BCC,EXTRA_SUBJECT. 只需要接受方完成响应数据接受。
     *
     * @param activityTitle
     *		  Activity的名字
     * @param msgTitle
     *		  消息标题
     * @param msgText
     *		  消息内容
     * @param imgFilePath
     *		  图片路径，不分享图片则传null
     */
    public static void sysShareMsg(Activity context,String activityTitle, String msgTitle, String msgText,
                                   String imgFilePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgFilePath == null || imgFilePath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgFilePath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }


    public static void sysRecord(){
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(intent);
    }

    public static void sysOpenApp(String packageName){
        try {
            PackageManager packageManager = BaseUtils.getContext().getPackageManager();
            Intent intent =  packageManager.getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goWangdoujia(packageName);
        }

    }

    private static void goYingyongbao(String packageName){
        //sysShowUrl("http://android.myapp.com/myapp/detail.htm?apkName="+ packageName);//电脑版
        int id = 89889;
        sysShowUrl("http://app.qq.com/id=detail&appid="+ id);
    }

    public static void goWangdoujia(String packageName){
        sysShowUrl("http://www.wandoujia.com/apps/"+ packageName);
    }

    public static void sysSearch(String content){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(intent);
    }


    public static void sysAppDetail(String packageName){
        if (PackageUtils.isApkIntalled(BaseUtils.getContext(),packageName)){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtils.getContext().startActivity(intent);
        }else {
            sysMarketDetail(packageName);
        }


    }

    public static void sysSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_HOME_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtils.getContext().startActivity(intent);
    }

    public static void goHome(Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);

    }




}
