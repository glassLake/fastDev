package com.hss01248.tools.dadabus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.pack.notification.MyNotification;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class DadaBusTicketService extends Service {


    private static final long PERIOD = 30 *60* 1000;//15min
    private TimerTask task;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("DadaBusTicketService onCreate");

        startTimerTask();



    }

    private void startTimerTask() {
        if (timer == null){
            timer = new Timer();
        }
        if (task == null){
            task = new TimerTask() {
                @Override
                public void run() {
                DaDaBus.getTicket();

                }
            };
        }

        timer.schedule(task,0,PERIOD);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyNotification.showService(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyNotification.clear(MyNotification.ID_SERVICE);
        timer.cancel();
        final Intent intent = new Intent(BaseUtils.getContext(), DadaBusTicketService.class);
        startService(intent);
    }
}
