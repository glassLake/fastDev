package com.hss01248.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.hss01248.tools.R;
import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.pack.image.fresco.FrescoUtils;
import com.hss01248.tools.pack.toast.MyToast;
import com.hss01248.tools.pack.toast.SuperCustomToast;
import com.hss01248.tools.pack.utils.FileUtils;
import com.hss01248.tools.pack.utils.IntentUtils;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class ToastDemoActivity extends Activity {
    Handler mHandler;
    Button showToast1;
    Button showToast2;
    Button showToast3;
    Button showToast4;
    Button showToast5;
    Button showToast6;
    Button showToast7;
    
    private  Activity context ;

    SuperCustomToast toast;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_toast);
        context = this;
        mHandler = new Handler();

        showToast1 = (Button) findViewById(R.id.showToast1);
        showToast2 = (Button) findViewById(R.id.showToast2);
        showToast3 = (Button) findViewById(R.id.showToast3);
        showToast4 = (Button) findViewById(R.id.showToast4);
        showToast5 = (Button) findViewById(R.id.showToast5);
        showToast6 = (Button) findViewById(R.id.showToast6);
        showToast7 = (Button) findViewById(R.id.showToast7);

        toast = SuperCustomToast.getInstance(getApplicationContext());
        final StringBuffer sb = new StringBuffer("默认Toast");
        final String info = "默认Toast-";
        final String sameString = "相同信息Toast";
        /**
         * 默认Toast
         */
        showToast1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FrescoUtils.download("https://americanbridgepac.org/app/uploads/unnamed-6.gif",
                        BaseUtils.getContext(),
                        FileUtils.getDownloadImageDir(),
                        new FrescoUtils.DownloadListener() {
                            @Override
                            public void onSuccess(File file) {
                                MyToast.showSuccessToast("下载成功："+ file.getAbsolutePath());
                            }

                            @Override
                            public void onFail() {
                                MyToast.showFailToast("下载失败");
                            }

                            @Override
                            public void onProgress(float progress) {
                                Logger.d("progress:"+progress*100);
                            }
                        });

            }
        });
        /**
         * 自定义Toast,5秒
         */
        showToast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //持续5000毫秒
//                toast.show(info + i++, 5000);
                //toast.showSameMsg(sameString, 5000);
               FrescoUtils.clearDiskCache();

            }
        });

        /**
         * 设置背景颜色的Toast+文字
         */
        showToast3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentUtils.sysMarketDetail("com.qxinli.android");
            }
        });
        /**
         * 设置背景图片的Toast
         */
        showToast4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               IntentUtils.sysAppDetail("com.qxinli.android");
            }
        });

        /**
         * 自定义动画
         */
        showToast5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               IntentUtils.sysSearch("15989369965");
            }
        });

        /**
         * //显示应用Logo
         */
        showToast6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            IntentUtils.sysShowUrl("http://www.baidu.com");
            }
        });
        /**
         * 自定义布局
         */
        showToast7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               IntentUtils.sysUnistallApk("com.qxinli.android");

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        toast.hideToast();
        toast.mView.removeAllViews();
        toast.initView();
    }
}
