package com.hss01248.tools.pack.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyJSON {

    public static String toJsonStr(Object obj){
        return JSON.toJSONString(obj);
    }


    public static <T> T  parseObject(String str,Class<T> clazz){
        return JSON.parseObject(str,clazz);
    }

    public static  <E> List<E> parseArray(String str,Class<E> clazz){
        return JSON.parseArray(str,clazz);
    }


}
