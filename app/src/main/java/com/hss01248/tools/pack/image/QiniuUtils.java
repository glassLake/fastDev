package com.hss01248.tools.pack.image;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class QiniuUtils {
    public final static String QINIU_TEST2 = "http://static-dev.qxinli.com/";
    public final static String QINIU_TEST = "http://7xnwnf.com2.z0.glb.qiniucdn.com/";
    public final static String QINIU_WWW ="http://static.qxinli.com/";
    public static  boolean isWWW;


    public static boolean isQiniu(String url){
        boolean isQiniu= false;
        if (TextUtils.isEmpty(url)){
            return  false;
        }
        isQiniu = url.contains(QINIU_WWW) || url.contains(QINIU_TEST) || url.contains(QINIU_TEST2)
                || url.contains("?imageMogr2") || url.contains("?imageView2") ||url.contains("?imageView");
        return isQiniu;

    }


    public static String getOriginUrl(String url,boolean isWWW){
        String newUrl = url;
        Logger.e("getUrl:"+ newUrl);
        if (TextUtils.isEmpty(url)){
            return "";
        }

        boolean hasHost = url.contains(QINIU_WWW ) || url.contains(QINIU_TEST ) || url.contains(QINIU_TEST2 ) ;
        if (!hasHost){
            if (isWWW){
                newUrl = QINIU_WWW + url;
            }else {
                newUrl = QINIU_TEST2 + url;
            }
        }

        if (newUrl.contains("?") ){
            newUrl = newUrl.substring(0,newUrl.indexOf("?"));
        }
           /* if (newUrl.contains("?imageView") ){
                newUrl = newUrl.substring(0,newUrl.indexOf("?imageView"));
            }else if (newUrl.contains("?imageMogr2")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?imageMogr2"));
            }else if (newUrl.contains("?facecrop2")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?facecrop2"));
            }else if (newUrl.contains("?imageInfo")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?imageInfo"));
            }else if (newUrl.contains("?watermark")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?watermark"));
            }else if (newUrl.contains("?imageAve")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?imageAve"));
            }else if (newUrl.contains("?exif")){
                newUrl = newUrl.substring(0,newUrl.indexOf("?exif"));
            }*/

        Logger.e("getUrlOrigin:"+ newUrl);

        return newUrl;
    }

    public static String getSamllImage(String url,int width,int height,boolean isWWW){
        if (TextUtils.isEmpty(url) || width ==0 || height ==0){
            return "";
        }
        String newUrl = url;
        StringBuilder sb = new StringBuilder(200);

        String originUrl = getOriginUrl(newUrl,isWWW);
        sb.append(originUrl);
        // 限定短边，生成不小于200x200的缩略图
        //  http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageMogr2/thumbnail/!200x200r
       // String imageEnd = "?imageMogr2/thumbnail/!"+"!"+width + "x"+ height +"r";
        sb.append("?imageMogr2/thumbnail/!");
        sb.append(width+"");
        sb.append("x");
        sb.append(height+"");
        sb.append("r");

        newUrl = sb.toString();
        Logger.e("getUrlsmall:"+ newUrl);
        return newUrl;


    }

    /**
     * 先生成缩略图，然后将缩略图模糊成基本色块，完全看不清图像
     * @param url
     * @param isWww
     * @return
     */
    public static String highBlur(String url,boolean isWww){
        String newUrl = getOriginUrl(url,isWww);
        return newUrl+"?imageMogr2/thumbnail/!300x200r/blur/50x99";

    }
}
