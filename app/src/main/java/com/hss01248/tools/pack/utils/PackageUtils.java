package com.hss01248.tools.pack.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import com.hss01248.tools.base.BaseUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

public class PackageUtils {
	protected static final String TAG = PackageUtils.class.getSimpleName();



	public static boolean isApplicationBroughtToBackground(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				//Logger.e("后台");
				return true;
			}
		}
		//Logger.e("前台");
		return false;
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					 Logger.e("后台"+ appProcess.processName);
					return true;
				}else{
					Logger.e("前台"+appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 安装apk应用
	 */
	public static void installAPK(Context context, File apkFile) {
		if (apkFile.isFile()) {
			String fileName = apkFile.getName();
			String postfix = fileName.substring(fileName.length() - 4, fileName.length());
			if (postfix.toLowerCase().equals(".apk")) {
				String cmd = "chmod 755 " + apkFile.getAbsolutePath();
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (Exception e) {
					Logger.e(TAG, e.getLocalizedMessage());
				}
				Uri uri = Uri.fromFile(apkFile);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(uri, "application/vnd.android.package-archive");
				context.startActivity(intent);
			}
		} else if (apkFile.isDirectory()) {
			File[] files = apkFile.listFiles();
			int fileCount = files.length;
			for (int i = 0; i < fileCount; i++) {
				installAPK(context, files[i]);
			}
		}
	}

	/**
	 * 安装apk应用
	 */
	public static void installDirApk(Context context, String filePath) {
		File file = new File(filePath);
		installAPK(context, file);
	}

	/**
	 * 卸载apk文件
	 */
	public static void uninstallPackage(Context context, Uri packageUri) {
		Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
		context.startActivity(intent);
	}

	/**
	 * 根据包得到应用信息
	 */
	public static ApplicationInfo getApplicationInfoByName(Context context, String packageName) {
		if (null == packageName || "".equals(packageName)) {
			return null;
		}
		try {
			return context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			Logger.e("EspanceUtils", packageName + " NameNotFoundException");
			return null;
		}
	}

	/**
	 *  通过报名获取包信息
	 */
	public static PackageInfo getPackageInfoByName(Context context, String packageName) {
		if (null == packageName || "".equals(packageName)) {
			return null;
		}
		try {
			return context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			Logger.e(TAG, e.getLocalizedMessage());
			return null;
		}
	}

	public static  String getThisVersionName(){
		PackageInfo info = getPackageInfoByName(BaseUtils.getContext(),BaseUtils.getContext().getPackageName());
		if (info != null){
			return info.versionName;
		}
		return "";
	}

	public static  int getThisVersionCode(){
		PackageInfo info = getPackageInfoByName(BaseUtils.getContext(),BaseUtils.getContext().getPackageName());
		if (info != null){
			return info.versionCode;
		}
		return 0;
	}

	/**
	 * 判断apk包是否安装
	 */
	public static boolean isApkIntalled(Context context, String packageName) {
		if (null == getApplicationInfoByName(context, packageName)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检测正式版,国际版,hd版,不考虑轻聊版,因为友盟第三方不支持轻聊版
	 * @return
	 */
	public static boolean isQQInstalled(){
		boolean isInstall = false;
		isInstall = isApkIntalled(BaseUtils.getContext(),"com.tencent.mobileqq") ||
				isApkIntalled(BaseUtils.getContext(),"com.tencent.mobileqqi") ||
				isApkIntalled(BaseUtils.getContext(),"com.tencent.minihd.qq");
		return isInstall;
	}

	public static boolean isQZoneInstalled(){
		boolean isInstall = false;
		isInstall = isQQInstalled() || isApkIntalled(BaseUtils.getContext(),"com.qzone");
		return isInstall;
	}

	public static boolean isWeiXinInstalled(){
		return isApkIntalled(BaseUtils.getContext(),"com.tencent.mm");
	}

	/**
	 * 包括正式版,4G版,HD版
	 * @return
	 */
	public static boolean isSinaInstalled(){
		boolean isInstall = false;
		isInstall = isApkIntalled(BaseUtils.getContext(),"com.sina.weibo") ||
				isApkIntalled(BaseUtils.getContext(),"com.sina.weibog3") ||
				isApkIntalled(BaseUtils.getContext(),"com.sina.weibotab");
		return isInstall;
	}


	/**
	 * 判断某个界面是否在前台
	 *
	 * @param context
	 * @param className
	 *            某个界面名称
	 */
	public static boolean isActivityForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			Logger.e("当前activity："+cpn.getClassName());
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}

		return false;
	}


	public static void restartApplication() {
		final Intent intent = BaseUtils.getContext().getPackageManager().getLaunchIntentForPackage(BaseUtils.getContext().getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		BaseUtils.getContext().startActivity(intent);
	}
}
