package com.hss01248.tools.pack.image.fresco;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hss01248.tools.pack.image.MyImageUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by yuan on 2015/7/21.
 */
public class FrescoImageloadHelper {

    public static void LoadImageFromURL(SimpleDraweeView destImageView , String URL)
    {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(URL))
                        .setResizeOptions(new ResizeOptions(destImageView.getLayoutParams().width, destImageView.getLayoutParams().height))
                                .setProgressiveRenderingEnabled(true)
                                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(destImageView.getController())
                .setAutoPlayAnimations(true)
                .build();
        destImageView.setController(draweeController);
    }
    public static void LoadImageFromURLAndCallBack(String URL,Context context,BaseBitmapDataSubscriber bbds)
    {

        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(URL))
                        .setProgressiveRenderingEnabled(true)
                        .build();


        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(bbds, CallerThreadExecutor.getInstance());
     /*   DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(destImageView.getController())
                .setAutoPlayAnimations(true)
                .build();
        destImageView.setController(draweeController);*/
    }
    public static void LoadFullImageFromURL(SimpleDraweeView destImageView , String URL)
    {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(URL))
                        .setResizeOptions(new ResizeOptions(destImageView.getLayoutParams().width, destImageView.getLayoutParams().height))
                        .setProgressiveRenderingEnabled(true)
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(destImageView.getController())
                .setAutoPlayAnimations(true)
                .build();
        destImageView.setController(draweeController);
    }
    /*
    *   简单的图片下载，用于长宽策略不固定的情况
     */
    public static  void simpleLoadImageFromURL(SimpleDraweeView destImageView , String URL)
    {
        if(URL !=null && Uri.parse(URL)!=null){
            destImageView.setImageURI(Uri.parse(URL));
        }

    }
    /**
     * 有些图片在网络上会变化，我们需要首现清除缓存从磁盘
     */
    public static  void removeCacheFromDisk(String imageURL)
    {
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().remove(new SimpleCacheKey(imageURL));
        Fresco.getImagePipelineFactory().getSmallImageDiskStorageCache().remove(new SimpleCacheKey(imageURL));
    }

    public  static void getFrescoBitmap(Uri imageUri, Activity mContext, final SimpleDraweeView destImageView){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource( imageUri)
                        //.setResizeOptions(new ResizeOptions(destImageView.getLayoutParams().width, destImageView.getLayoutParams().height))
                        .setProgressiveRenderingEnabled(true)
                        .build();

        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, mContext);

        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(@Nullable Bitmap bitmap) {
                    if (bitmap == null) {
                     //   Log.d(TAG, "Bitmap data source returned success, but bitmap null.");
                        return;
                    }

                    Bitmap bitmap1 = MyImageUtils.BoxBlurFilter(bitmap);
                    Logger.d(bitmap1.toString());

                    destImageView.setImageBitmap(bitmap1);

                }

                @Override
                public void onFailureImpl(DataSource dataSource) {
                    // No cleanup required here
                }
            }, CallerThreadExecutor.getInstance());
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }
    }
}
