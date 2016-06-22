package com.hss01248.tools.dadabus;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.hss01248.tools.R;
import com.hss01248.tools.base.BaseUtils;
import com.hss01248.tools.config.Url;
import com.hss01248.tools.pack.net.MyNetListener;
import com.hss01248.tools.pack.net.MyNetUtils;
import com.hss01248.tools.pack.notification.MyNotification;
import com.hss01248.tools.pack.toast.MyToast;
import com.hss01248.tools.pack.utils.IntentUtils;
import com.hss01248.tools.pack.utils.MyJSON;
import com.hss01248.tools.pack.utils.VibratorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class DaDaBus {


    /*
    * http://api.buskeji.com/line/real_line_baseinfo
    * ?line_code=1180-227-7759
    * &start_date
    * &version=2.5.0
    * &device_id=868961020322214
    * &device_uuid=fdc134e4-618e-4754-ac11-4f2c1e1b508f
    * &login_type=1
    * &device_type=1
    * &source=1
    * &city_code=0755
    * &lat=22.530829
    * &lng=113.944407
    * &device_model=Cong+C1391
    * &sys_version=5.1
    * &gps_sampling_time=1466563269
    * &ddb_token=
    * &cache_time=1466567534045
    * &screen=1080%3A1800%7C3.0 */

    public static void getTicket(){
        Map<String,String> map = new HashMap<>();
        map.put("line_code","1180-137-7759");
        map.put("start_date","");
        map.put("version","2.5.0");
        map.put("device_id","868961020322214");
        map.put("device_uuid","fdc134e4-618e-4754-ac11-4f2c1e1b508f");
        map.put("login_type","1");
        map.put("device_type","1");
        map.put("source","1");
        map.put("city_code","0755");
        map.put("lat","22.530829");
        map.put("lng","113.944407");
        map.put("device_model","Cong+C1391");
        map.put("sys_version","5.1");
        map.put("gps_sampling_time","1466563269");
        map.put("ddb_token","");
        map.put("cache_time","1466567534045");
        map.put("screen","1080%3A1800%7C3.0");

        MyNetUtils.getJson(Url.BUS_DADA, map, "bus", new MyNetListener() {
            @Override
            public void onSuccess(Object response, String optStr) {
                LineInfo info = MyJSON.parseObject(optStr,LineInfo.class);
                List<LineInfo.DataBean.TicketInfoBean> list = info.data.ticket_info;

                if (list!= null){
                    if (list.size()==1){
                       /* PackageManager packageManager = BaseUtils.getContext().getPackageManager();
                        Intent intent =  packageManager.getLaunchIntentForPackage("com.newdadabus");
                        if (intent != null){
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(BaseUtils.getContext(),1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            MyNotification.sho(pendingIntent, R.mipmap.ic_launcher,"月票没有","没有月票","还没有放出月票");
                        }*/
                       /* MyToast.showFailToast("还没有放票");

                        VibratorUtil.Vibrate(2000);*/



                    }else if (list.size() >1){

                        PackageManager packageManager = BaseUtils.getContext().getPackageManager();
                        Intent intent =  packageManager.getLaunchIntentForPackage("com.newdadabus");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(BaseUtils.getContext(),1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        MyNotification.showSound(pendingIntent, R.mipmap.ic_launcher,"月票出来了","月票出来了","月票出来了");
                        MyToast.showSuccessToast("放票了放票了");
                        IntentUtils.sysOpenApp("com.newdadabus");
                        long[] partten = new long[]{2000,6000,2000,9000};
                        VibratorUtil.Vibrate(partten,true);

                    }
                }
            }
        });


    }

    /*http://api.buskeji.com/line/real_line_schedule_list
    ?line_code=1180-137-7759
    &version=2.4.1&
    user_id=764085
    &mobile=15989369965
    &device_id=868961020322214&
    device_uuid=fdc134e4-618e-4754-ac11-4f2c1e1b508f&
    login_type=1
    &device_type=1
    &source=1&
    city_code=0755
    &lat=22.571762&
    lng=113.880826&
    device_model=Cong+C1391
    &sys_version=5.1
    &gps_sampling_time=1464193575
    &ddb_token=503b63911745d97a08fdc861b7ab630a
    &cache_time=1464193588567
    &screen=1080%3A1800%7C3.0*/

}
