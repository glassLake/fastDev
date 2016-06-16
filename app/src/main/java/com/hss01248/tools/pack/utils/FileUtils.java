package com.hss01248.tools.pack.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.pack.toast.MyToast;
import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
	protected static final String TAG = FileUtils.class.getSimpleName();

	public static String getFileSizeStr(Context context,long size){
		return Formatter.formatFileSize(context,size);
	}






	/*public static String[] getAllSdCards(){

		StorageManager sm = (StorageManager) UIUtils.getContext().getSystemService(Context.STORAGE_SERVICE);
		String[] paths = new String[]{};
// 获取sdcard的路径：外置和内置
		try {
			paths = (String[]) sm.getClass().getMethod("getVolum" + " " +
					"ePaths",null).invoke(sm, new Object[]{});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return paths;
	}*/

	/**文件重命名
	 * @param path 文件目录
	 * @param oldname  原来的文件名
	 * @param newname 新文件名
	 */
	public static void renameFile(String path,String oldname,String newname){
		if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile=new File(path+"/"+oldname);
			File newfile=new File(path+"/"+newname);
			if(!oldfile.exists()){
				return;//重命名文件不存在
			}
			if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname+"已经存在！");
			else{
				oldfile.renameTo(newfile);
			}
		}else{
			System.out.println("新文件名和旧文件名相同...");
		}
	}



	//保存字符串到文件中
	public static void saveAsFileInSDCard(String content,String fileName) {

		File file = new File(getCacheSubDir("0cacheResponse"),fileName);
		FileWriter fwriter = null;
		try {
			fwriter = new FileWriter(file);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}




	/**
	 * 获取手机sd卡下的某一个特定名称的文件夹
	 * @param uniqueName
	 * @return
	 */
	public static File getSdRootSubDir(String uniqueName) {
		File dir;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			dir = Environment.getExternalStorageDirectory();
			if (dir == null){
				dir = BaseUtils.getContext().getCacheDir();
			}
		} else {
			dir = BaseUtils.getContext().getCacheDir();
		}

		dir = new File(dir.getAbsolutePath(), uniqueName);
		if (!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}

	public static File getSdRootDirByPkgName(){
		/*String pkg = UIUtils.getPackageName();
		String[] pkgs = pkg.split(".");
		String pkgss = pkgs[pkgs.length -1];*/
		return getSdRootSubDir("qxinli");
	}

	public static  File getCacheSubDir(String uniqueName){
		File file = BaseUtils.getContext().getCacheDir();
		//File file = UIUtils.getContext().getCacheDir();
		File dir = new File(file,uniqueName);
		if (!dir.exists()){
			dir.mkdirs();
			//changeDirPermissionToWorldWritable(dir);
		}

		return dir;

	}

	public static File saveBitmap(Bitmap bm ) throws IOException {
		File file = getSdRootDirByPkgName();
		if(!file.exists()){
			file.mkdir();
		}
		 File saveFile = new File(file.getAbsolutePath() + "/boxblurbitmap");
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return  saveFile;
	}
	private static void changeDirPermissionToWorldWritable(File dir) {
		Process p;
		int status;
		try {
			p = Runtime.getRuntime().exec("chmod 777 " +  dir );
			status = p.waitFor();
			if (status == 0) {
				//chmod succeed
				Logger.e("chmod succeed");
			} else {
				//chmod failed
				Logger.e("chmod failed ");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void chageFilePermission(File destFile){
		try {
			String command = "chmod 777 " + destFile.getAbsolutePath();
			Log.i("zyl", "command = " + command);
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(command);
		} catch (IOException e) {
			Log.i("zyl","chmod fail!!!!");
			e.printStackTrace();
		}
	}


	/**
	 * 获取到手机sd卡上指定名称的缓存路径
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			File path = context.getExternalCacheDir();
			if (path != null){
				cachePath = path.getPath();
			}else {
				cachePath = context.getCacheDir().getPath();
			}
		} else {
			cachePath = context.getCacheDir().getPath();
		}


		File file = new File(cachePath + File.separator + uniqueName);

		if (!file.exists()){
			file.mkdirs();
		}



		return file;
	}

	/**
	 * 获取到手机sd卡上指定名称的缓存路径

	 * @return
	 */
	public static File getDiskCacheRootDir(Context context) {
		File  cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir();
		} else {
			cachePath = context.getCacheDir();
		}
		return cachePath;
	}


	/**
	 * 获取外置SD卡路径
	 * @return  应该就一条记录或空
	 */
	public static List<String> getExtSDCardPath() {
		List<String> lResult = new ArrayList<String>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("extSdCard"))
				{
					String [] arr = line.split(" ");
					String path = arr[1];
					File file = new File(path);
					if (file.isDirectory())
					{
						lResult.add(path);
						Logger.e("path:" + path);
						MyToast.showDebugToast(path);
					}
				}
			}
			isr.close();
		} catch (Exception e) {
		}
		return lResult;
	}


	/**
	 * 获取到手机sd卡上指定名称的缓存路径
	 * @return
	 */
	public static File getAppCacheDir() {
		File cacheDir;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			File path = BaseUtils.getContext().getExternalCacheDir();
			if (path != null){
				cacheDir = path;
			}else {
				cacheDir = BaseUtils.getContext().getCacheDir();
			}
		} else {
			cacheDir = BaseUtils.getContext().getCacheDir();
		}




		return cacheDir;
	}


	/**
	 * 传入url,生成diskLrucache需要的int类型的index
	 * @param key
	 * @return
	 */
	public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}



	/**
	 * 获取SD卡路径
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	/**
	 * 将long类型的文件大小(字节)转换为带单位的String类型
	 * @param size
	 * @return
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	/**
	 *  判断SD卡是否可用
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 *  获取SD卡剩余空间
	 * @return
	 */
	public static long getSDFreeSize() {
		if (isSDCardAvailable()) {
			StatFs statFs = new StatFs(getSDCardPath());

			long blockSize = statFs.getBlockSize();

			long freeBlocks = statFs.getAvailableBlocks();
			return freeBlocks * blockSize;
		}

		return 0;
	}

	/**
	 *  获取SD卡的总容量
	 * @return
	 */
	public static long getSDAllSize() {
		if (isSDCardAvailable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 拷贝文件，通过返回值判断是否拷贝成功
	 * @param sourcePath
	 *            源文件路径
	 * @param targetPath
	 *            目标文件路径
	 * @return
	 */
	public static boolean copyFile(String sourcePath, String targetPath) {
		boolean isOK = false;
		if (!TextUtils.isEmpty(sourcePath) && !TextUtils.isEmpty(targetPath)) {
			File sourcefile = new File(sourcePath);
			File targetFile = new File(targetPath);
			if (!sourcefile.exists()) {
				return false;
			}
			if (sourcefile.isDirectory()) {
				isOK = copyDir(sourcefile, targetFile);
			} else if (sourcefile.isFile()) {
				if (!targetFile.exists()) {
					createFile(targetPath);
				}
				FileOutputStream outputStream = null;
				FileInputStream inputStream = null;
				try {
					inputStream = new FileInputStream(sourcefile);
					outputStream = new FileOutputStream(targetFile);
					byte[] bs = new byte[1024];
					int len;
					while ((len = inputStream.read(bs)) != -1) {
						outputStream.write(bs, 0, len);
					}
					isOK = true;
				} catch (Exception e) {
					Log.i(TAG, e.getLocalizedMessage());
					Log.i(TAG, e.getLocalizedMessage());
					isOK = false;
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							Log.i(TAG, e.getLocalizedMessage());
						}
					}
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							Log.i(TAG, e.getLocalizedMessage());
						}
					}
				}
			}

			return isOK;
		}
		return false;
	}

	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			if (!file.exists()) {
				return false;
			}
			try {
				file.delete();
			} catch (Exception e) {
				Log.i(TAG, e.getLocalizedMessage());
				return false;
			}
			return true;
		}
		return false;
	}

	public static String getSizeStr(File file){
		return  convertFileSize(getSize(file));

	}

	/**
	 * 统计文件夹文件的大小
	 */
	public static long getSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
			if (!file.isFile()) {
				// 获取文件大小
				File[] fl = file.listFiles();
				long ss = 0;
				for (File f : fl)
					ss += getSize(f);
				return ss;
			} else {
				long ss = (long) file.length();
				return ss; // 单位制bytes
			}
		} else {
			// System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0;
		}
	}

	/**
	 * 把bytes转换成MB
	 */
	public static String getTrafficStr(long total) {
		Logger.e("byte:"+ total );
		DecimalFormat format = new DecimalFormat("##0.0");
		if (total < 1024 * 1024) {
			return format.format(total / 1024f) + "KB";
		} else if (total < 1024 * 1024 * 1024) {
			return format.format(total / 1024f / 1024f) + "MB";
		} else if (total < 1024 * 1024 * 1024 * 1024) {
			return format.format(total / 1024f / 1024f / 1024f) + "GB";
		} else {
			return "统计错误";
		}
	}

	/**
	 * 删除文件夹里面的所以文件
	 */
	public static void deleteDir(File dir) {
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				} else {
					deleteDir(files[i]);
				}
			}
		}
	}

	/**
	 * 剪切文件，将文件拷贝到目标目录，再将源文件删除
	 * @param sourcePath
	 * @param targetPath
	 */
	public static boolean cutFile(String sourcePath, String targetPath) {
		boolean isSuccessful = copyFile(sourcePath, targetPath);
		if (isSuccessful) {
			// 拷贝成功则删除源文件
			return deleteFile(sourcePath);
		}
		return false;
	}

	/**
	 *  拷贝目录
	 * @param sourceFile
	 * @param targetFile
	 * @return
	 */
	public static boolean copyDir(File sourceFile, File targetFile) {
		if (sourceFile == null || targetFile == null) {
			return false;
		}
		if (!sourceFile.exists()) {
			return false;
		}
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 获取目录下所有文件和文件夹的列表
		File[] files = sourceFile.listFiles();
		if (files == null || files.length < 1) {
			return false;
		}
		File file = null;
		StringBuffer buffer = new StringBuffer();
		boolean isSuccessful = false;
		// 遍历目录下的所有文件文件夹，分别处理
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			buffer.setLength(0);
			buffer.append(targetFile.getAbsolutePath()).append(File.separator).append(file.getName());
			if (file.isFile()) {
				// 文件直接调用拷贝文件方法
				isSuccessful = copyFile(file.getAbsolutePath(), buffer.toString());
				if (!isSuccessful) {
					return false;
				}
			} else if (file.isDirectory()) {
				// 目录再次调用拷贝目录方法
				copyDir(file, new File(buffer.toString()));
			}

		}
		return true;
	}

	/**
	 * 剪切目录，先将目录拷贝完后再删除源目录
	 * @param sourceDir
	 * @param targetDir
	 * @return
	 */
	public static boolean cutDir(String sourceDir, String targetDir) {
		File sourceFile = new File(sourceDir);
		File targetFile = new File(targetDir);
		boolean isCopySuccessful = copyDir(sourceFile, targetFile);
		if (isCopySuccessful) {
			return deleteDir(sourceDir);
		}
		return false;
	}

	/**
	 * 删除目录
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return false;
		}
		File[] files = file.listFiles();
		boolean isSuccessful = false;
		if (files.length == 0) {
			file.delete();
			return true;
		}
		// 对所有列表中的路径进行判断是文件还是文件夹
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				isSuccessful = deleteDir(files[i].getAbsolutePath());
			} else if (files[i].isFile()) {
				isSuccessful = deleteFile(files[i].getAbsolutePath());
			}

			if (!isSuccessful) {
				// 如果有删除失败的情况直接跳出循环
				break;
			}
		}
		if (isSuccessful) {
			file.delete();
		}
		return isSuccessful;
	}

	/**
	 * 
	 * 将流写入指定文件
	 * @param inputStream
	 * @param path
	 * @return
	 */
	public static boolean stream2File(InputStream inputStream, String path) {
		File file = new File(path);
		boolean isSuccessful = true;
		FileOutputStream fileOutputStream = null;
		try {
			if (!file.exists()) {
				File file2 = file.getParentFile();
				file2.mkdirs();
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			byte[] bs = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(bs)) != -1) {
				fileOutputStream.write(bs, 0, length);
			}
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
			isSuccessful = false;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				Log.i(TAG, e.getLocalizedMessage());
			}
		}
		return isSuccessful;
	}

	/**
	 * 创建目录
	 * @param path
	 */
	public static void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 修改文件读写权限
	 * @param fileAbsPath
	 * @param mode
	 */
	public static void chmodFile(String fileAbsPath, String mode) {
		String cmd = "chmod " + mode + " " + fileAbsPath;
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * 将object对象写入outFile文件
	 * @param outFile
	 * @param object
	 * @param context
	 */
	public static void writeObject2File(String outFile, Object object, Context context) {
		ObjectOutputStream out = null;
		FileOutputStream outStream = null;
		try {
			File dir = context.getDir("cache", Context.MODE_PRIVATE);
			outStream = new FileOutputStream(new File(dir, outFile));
			out = new ObjectOutputStream(new BufferedOutputStream(outStream));
			out.writeObject(object);
			out.flush();
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					Log.i(TAG, e.getLocalizedMessage());
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Log.i(TAG, e.getLocalizedMessage());
				}
			}
		}
	}

	/**
	 * 
	 * 从outFile文件读取对象
	 * @param filePath
	 * @param context
	 * @return
	 */
	public static Object readObjectFromPath(String filePath, Context context) {
		Object object = null;
		ObjectInputStream in = null;
		FileInputStream inputStream = null;
		try {
			File dir = context.getDir("cache", Context.MODE_PRIVATE);
			File f = new File(dir, filePath);
			if (f == null || !f.exists()) {
				return null;
			}
			inputStream = new FileInputStream(new File(dir, filePath));
			in = new ObjectInputStream(new BufferedInputStream(inputStream));
			object = in.readObject();
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.i(TAG, e.getLocalizedMessage());
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.i(TAG, e.getLocalizedMessage());
				}
			}

		}
		return object;
	}

	/**
	 * 读取指定路径下的文件内容
	 * @param path
	 * @return 文件内容
	 */
	public static String readFile(String path) {
		BufferedReader br = null;
		try {
			File myFile = new File(path);
			br = new BufferedReader(new FileReader(myFile));
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					Log.i(TAG, e.getLocalizedMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 创建文件，并修改读写权限
	 * @param filePath
	 * @param mode
	 * @return
	 */
	public static File createFile(String filePath, String mode) {
		File desFile = null;
		try {
			String desDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
			File dir = new File(desDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			chmodFile(dir.getAbsolutePath(), mode);
			desFile = new File(filePath);
			if (!desFile.exists()) {
				desFile.createNewFile();
			}
			chmodFile(desFile.getAbsolutePath(), mode);
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage());
		}
		return desFile;
	}

	/**
	 * 根据指定路径，创建父目录及文件
	 * @param filePath
	 * @return File 如果创建失败的话，返回null
	 */
	public static File createFile(String filePath) {
		return createFile(filePath, "755");
	}

	/**
	 * 获取系统存储路径
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * 获取外部存储路径
	 * @return
	 */
	public static String getExternalStorageDirectoryPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
	 *
	 * @return
	 */
	private static ArrayList<String> getDevMountList() {
		String[] toSearch = FileUtils.readFile("/etc/vold.fstab").split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (int i = 0; i < toSearch.length; i++) {
			if (toSearch[i].contains("dev_mount")) {
				if (new File(toSearch[i + 2]).exists()) {
					out.add(toSearch[i + 2]);
				}
			}
		}
		return out;
	}

	/**
	 * 获取扩展SD卡存储目录
	 *
	 * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
	 * 否则：返回内置SD卡目录
	 *
	 * @return
	 */
	public static String getExternalSdCardPath() {

		/*if (SDCardUtils.isMounted()) {
			File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			return sdCardFile.getAbsolutePath();
		}*/

		String path = null;

		File sdCardFile = null;

		ArrayList<String> devMountList = getDevMountList();

		for (String devMount : devMountList) {
			File file = new File(devMount);

			if (file.isDirectory() && file.canWrite()) {
				path = file.getAbsolutePath();

				String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
				File testWritable = new File(path, "test_" + timeStamp);

				if (testWritable.mkdirs()) {
					testWritable.delete();
				} else {
					path = null;
				}
			}
		}

		if (path != null) {
			sdCardFile = new File(path);
			return sdCardFile.getAbsolutePath();
		}

		return null;
	}


	public static String getExtension(final File file) {
		String suffix = "";
		String name = file.getName();
		final int idx = name.lastIndexOf(".");
		if (idx > 0) {
			suffix = name.substring(idx + 1);
		}
		return suffix;
	}

	public static String getMimeType(final File file) {
		String extension = getExtension(file);
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
	}
}
