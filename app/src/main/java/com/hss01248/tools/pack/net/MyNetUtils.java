package com.hss01248.tools.pack.net;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class MyNetUtils {

    public static void getJson(String url, Map<String, String> map, String tag, final MyNetListener listener){
        HttpParams params = new HttpParams();
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
        for (Map.Entry<String,String> entry : entrySet){
            params.put(entry.getKey(),entry.getValue());
        }
//http header, optional parameters
      /*  params.putHeaders("cookie", "your cookie");
        params.putHeaders("User-Agent", "rxvolley");*/


//request parameters


        HttpCallback callBack = new HttpCallback(){
            @Override
            public void onSuccess(String t) {
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {
            }
        };

        new RxVolley.Builder()
                .url(url)
                .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                //.cacheTime(6) //default: get 5min, post 0min
                .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                .params(params)
                .shouldCache(false) //default: get true, post false
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        listener.onSuccess(t,t);
                    }



                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        listener.onError(strMsg);
                    }


                })
                .encoding("UTF-8") //default
                .setTag(tag)
                .doTask();
    }
}
