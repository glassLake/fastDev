package com.hss01248.tools.pack.image.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import jp.wasabeef.fresco.processors.BlurPostprocessor;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class FrescoUtils {

    private static final String PHOTO_FRESCO = "frescocache";


    /**
     * 高斯模糊后显示
     * @param url
     * @param isOnlyBlurHere 同一个url,是否仅仅在此处模糊:如果想避免其他地方与此url一致的组件也被高斯模糊,可以在url后面加上xx=yy
     * @param draweeView
     * @param width draweeView的宽
     * @param height draweeView的高
     * @param context
     * @param radius  高斯模糊的半径, 每一个像素都取周边(多少个)像素的平均值
     * @param sampling 采样率 原本是设置到BlurPostprocessor上的,因为高斯模糊本身对图片清晰度要求就不高,
     *                 所以此处直接设置到ResizeOptions上,直接让解码生成的bitmap就缩小,而BlurPostprocessor
     *                 内部sampling设置为1,无需再缩
     */
    public static void loadUrlInBlur(String url,boolean isOnlyBlurHere, SimpleDraweeView draweeView,int width,int height,Context context,int radius,int sampling){
        if (isOnlyBlurHere){
            if (url.contains("?")){
                url = url+ "&xx=yy";
            }else {
                url = url +"?xx=yy";
            }
        }

        if (sampling<2){
            sampling = 2;
        }
        loadUrl(url,draweeView,new BlurPostprocessor(context,radius,1),width/sampling,height/sampling,null);

    }


    public static void loadUrl(String url, SimpleDraweeView draweeView,BasePostprocessor processor,int width,int height,
                               BaseControllerListener listener){

        ImageRequest request =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                        .setPostprocessor(processor)
                        .setResizeOptions(new ResizeOptions(width,height))
                        //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                        .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                        .setAutoRotateEnabled(true) //如果图片是侧着,可以自动旋转
                        .build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setControllerListener(listener)
                        .setOldController(draweeView.getController())
                        .setAutoPlayAnimations(true) //自动播放gif动画
                        .build();

        draweeView.setController(controller);

    }

    /**
     * 当设置roundAsCircle为true无效时,采用这个方法,常用在gif的圆形效果上
     *
     * 或者在xml中设置:fresco:roundWithOverlayColor="@color/you_color_id"
     "you_color_id"是指你的背景色，这样也可以实现圆角、圆圈效果
     *
     *roundAsCircle的局限性:
     * 当使用BITMAP_ONLY（默认）模式时的限制：

     并非所有的图片分支部分都可以实现圆角，目前只有占位图片和实际图片可以实现圆角，我们正在努力为背景图片实现圆角功能。
     只有BitmapDrawable 和 ColorDrawable类的图片可以实现圆角。我们目前不支持包括NinePatchDrawable和 ShapeDrawable在内的其他类型图片。（无论他们是在XML或是程序中声明的）
     动画不能被圆角。
     由于Android的BitmapShader的限制，当一个图片不能覆盖全部的View的时候，边缘部分会被重复显示，而非留白。对这种情况可以使用不同的缩放类型
     （比如centerCrop）来保证图片覆盖了全部的View。 OVERLAY_COLOR模式没有上述限制，但由于这个模式使用在图片上覆盖一个纯色图层的方式来模拟圆角效果，
     因此只有在图标背景是静止的并且与图层同色的情况下才能获得较好的效果。
     * @param draweeView
     * @param bgColor 圆形遮罩的颜色,应该与背景色一致
     */
    public static void setCircle( SimpleDraweeView draweeView,int bgColor){
        RoundingParams roundingParams = RoundingParams.asCircle();//这个方法在某些情况下无法成圆,比如gif
        roundingParams.setOverlayColor(bgColor);//加一层遮罩
// 或用 fromCornersRadii 以及 asCircle 方法
        draweeView.getHierarchy().setRoundingParams(roundingParams);
    }



    /**
     * 初始化操作，建议在子线程中进行
     * @param context
     * @param cacheSizeInM  磁盘缓存的大小，以M为单位
     */
    public static void init(final Context context,int cacheSizeInM){


        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(cacheSizeInM*1024*1024)
                .setBaseDirectoryName(PHOTO_FRESCO)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return context.getCacheDir();
                    }
                })
                .build();
        MyImageCacheStatsTracker imageCacheStatsTracker = new MyImageCacheStatsTracker();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setDownsampleEnabled(true)//Downsampling，它处理图片的速度比常规的裁剪更快，
                // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                .build();
        Fresco.initialize(context, config);
    }


    /**
     *
     * 不要使用这个下载页面的gradle依赖配置，应该使用下面的依赖配置
     *compile: "com.facebook.fresco:drawee:0.1.0+"
     compile: "com.facebook.fresco:imagepipeline-okhttp:0.1.0+"
     * @param context
     * @param cacheSizeInM
     * @param okHttpClient
     */
    public static void initWithOkhttp(final Context context, int cacheSizeInM, OkHttpClient okHttpClient){

       /* DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(cacheSizeInM*1024*1024)
                .setBaseDirectoryName(PHOTO_FRESCO)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return context.getCacheDir();
                    }
                })
                .build();
        MyImageCacheStatsTracker imageCacheStatsTracker = new MyImageCacheStatsTracker();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context, okHttpClient)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setDownsampleEnabled(true)//Downsampling，它处理图片的速度比常规的裁剪更快，
                // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                .build();
        Fresco.initialize(context, config);*/
    }


    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(){
        Fresco.getImagePipeline().clearDiskCaches();
    }


    /**
     * 拿到指定宽高的bitmap
     * @param url
     * @param context
     * @param width
     * @param height
     * @param listener
     */
    public static void getBitmap(String url, Context context, int width, int height, final BitmapListener listener){
        getBitmapWithProcessor(url,context,width,height,null,listener);
    }


    /**
     * 拿到指定宽高，并经过Processor处理的bitmap
     * @param url
     * @param context
     * @param width
     * @param height
     * @param processor
     * @param listener
     *
     */
    public static void getBitmapWithProcessor(String url, Context context, int width, int height,
                                              BasePostprocessor processor,final BitmapListener listener){
        FrescoImageloadHelper.loadBitmap(url, context, width, height, processor,new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                listener.onSuccess(bitmap);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                listener.onFail();
            }
        });
    }

    /**
     * Created by hss01248 on 11/26/2015.
     */
    public static class MyImageCacheStatsTracker implements ImageCacheStatsTracker {
        @Override
        public void onBitmapCachePut() {

        }

        @Override
        public void onBitmapCacheHit() {

        }

        @Override
        public void onBitmapCacheMiss() {

        }

        @Override
        public void onMemoryCachePut() {

        }

        @Override
        public void onMemoryCacheHit() {

        }

        @Override
        public void onMemoryCacheMiss() {

        }

        @Override
        public void onStagingAreaHit() {

        }

        @Override
        public void onStagingAreaMiss() {

        }

        @Override
        public void onDiskCacheHit() {
            //Logger.e("ImageCacheStatsTracker---onDiskCacheHit");
        }

        @Override
        public void onDiskCacheMiss() {
            //Logger.e("ImageCacheStatsTracker---onDiskCacheMiss");
        }

        @Override
        public void onDiskCacheGetFail() {
            //Logger.e("ImageCacheStatsTracker---onDiskCacheGetFail");
        }

        @Override
        public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> countingMemoryCache) {

        }

        @Override
        public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> countingMemoryCache) {

        }
    }

    public interface BitmapListener{
        void onSuccess(Bitmap bitmap);
        void onFail();
    }
}
