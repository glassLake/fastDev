package com.hss01248.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.dadabus.DadaBusTicketService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements ServiceConnection {

    @Bind(R.id.btn_test)
    Button btnTest;

    MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);

        startservices();
    }

    private void startservices() {
        final Intent intent = new Intent(this, DadaBusTicketService.class);
        startService(intent);
        BaseUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                bindService(intent,context, BIND_AUTO_CREATE);
            }
        },9000);

    }

    @OnClick(R.id.btn_test)
    public void onClick() {
       /* Intent intent = new Intent(this, ToastDemoActivity.class);
        startActivity(intent);*/
       /* MyToast.showSuccessToast("成功！成功！");
        MyToast.showFailToast("发表失败，请重试");*/

       // RetrofitUtils.postTucaoList();



    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
