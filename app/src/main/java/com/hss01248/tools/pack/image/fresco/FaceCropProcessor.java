package com.hss01248.tools.pack.image.fresco;

import android.graphics.Bitmap;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.hss01248.tools.pack.image.face.FaceCropper;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class FaceCropProcessor extends BasePostprocessor {


    /**
     * todo 还有图片大小的改变,要如何维持图片大小不变？
     * @param sourceBitmap
     */
    @Override
    public void process(Bitmap sourceBitmap) {

        FaceCropper faceCropper = new FaceCropper();
        faceCropper.setDebug(false);
       Bitmap destBitmap = faceCropper.cropFace(sourceBitmap);

        super.process(destBitmap);
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return super.getPostprocessorCacheKey();
    }
}
