package com.hss01248.tools.pack.image.fresco;

import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;

/**
 * Created by hss01248 on 11/26/2015.
 */
public class MyImageCacheStatsTracker implements ImageCacheStatsTracker {
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
