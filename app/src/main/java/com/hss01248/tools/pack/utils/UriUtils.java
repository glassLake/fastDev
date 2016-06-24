package com.hss01248.tools.pack.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class UriUtils {

    /**
     * 注意无法识别超过1M的文件
     * @param fileName
     * @return
     */
    public static Uri getAssert(String fileName){
        return   Uri.parse("file:///android_asset/" + fileName);
    }

    public static Uri getRaw(Context context,int rawId){
      return   Uri.parse("android.resource://" + context.getPackageName() + "/" + rawId);
    }


}
