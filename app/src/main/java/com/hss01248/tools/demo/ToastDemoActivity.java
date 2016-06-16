package com.hss01248.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.hss01248.tools.R;
import com.hss01248.tools.pack.toast.SuperCustomToast;
import com.hss01248.tools.pack.utils.IntentUtils;

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
                IntentUtils.sysShareMsg(context,"shareTo","this is a sys share test","刚刚翻了翻答案发现原来真的是因为我长的不合适而不是公共设施不人性化啊啊啊啊！设计师我这么多年错怪你们真是对不起了！！！！因为从小身高体壮专修篮球所以身边200左右的还是颇有几只的，有些痛真的只有我们自己知道。比如进门一起低头，上公交默契的不坐座位，蹲坑关不上门等等。。。。在认识他们之前坐车副驾驶的位置指定都是我的，然而第一次一起出去玩打车的时候，我们三个人都下意识的去拉副驾门把手，那个尴尬你们懂吗？！！！从车窗只看到了三条裤腰的司机大哥一脸的惊恐啊！！！\n" +
                        "另附两张197好基友 @邹海艇 使用公共设施的状态\n" +
                        "\n" +
                        "\n" +
                        "作者：Alger\n" +
                        "链接：https://www.zhihu.com/question/26730775/answer/104921961\n" +
                        "来源：知乎\n" +
                        "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。","");

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
                IntentUtils.sysRecord();

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
