package com.hss01248.tools.dadabus;

import com.hss01248.tools.config.Url;
import com.hss01248.tools.pack.net.MyNetListener;
import com.hss01248.tools.pack.net.MyNetUtils;
import com.hss01248.tools.pack.utils.MyJSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class DaDaBus {

    public static void getTicket(){
        Map<String,String> map = new HashMap<>();
        map.put("line_code","1180-137-7759");
        map.put("version","2.4.1");
        map.put("mobile","15989369965");
        map.put("device_id","868961020322214");
        map.put("device_uuid","fdc134e4-618e-4754-ac11-4f2c1e1b508");
        map.put("login_type","1");
        map.put("device_type","1");
        map.put("source","1");
        map.put("city_code","0755");
        map.put("lat","22.571762");
        map.put("lng","113.880826");
        map.put("device_model","Cong+C1391");
        map.put("device_model","Cong+C1391");
        map.put("sys_version","5.1");
        map.put("gps_sampling_time","1464193575");
        map.put("ddb_token","503b63911745d97a08fdc861b7ab630a");
        map.put("cache_time","1464193588567");
        map.put("screen","1080:1800|3.0");

        MyNetUtils.getJson(Url.BUS_DADA, map, "bus", new MyNetListener() {
            @Override
            public void onSuccess(Object response, String optStr) {
                TickInfo info = MyJSON.parseObject(optStr,TickInfo.class);
                List<TickInfo.DataBean.ScheduleListBean> list = info.data.schedule_list;
                if (list!= null && list.size() > 0){
                    TickInfo.DataBean.ScheduleListBean listBean = list.get(0);
                   if (listBean.valid_seat >0){

                   }else {

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
