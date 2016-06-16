package com.hss01248.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hss01248.tools.demo.ToastDemoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.btn_test)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test)
    public void onClick() {
        Intent intent = new Intent(this, ToastDemoActivity.class);
        startActivity(intent);
       /* MyToast.showSuccessToast("成功！成功！");
        MyToast.showFailToast("发表失败，请重试");*/

    }
}
