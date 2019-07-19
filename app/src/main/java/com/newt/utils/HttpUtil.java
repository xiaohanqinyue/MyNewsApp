package com.newt.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * CCreated by Administrator on 2019/5/9.
 *   从服务器获取新闻数据
 */

public class HttpUtil {


    public static void sendHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request= new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);//发送请求并获取服务器返回的数据
    }


}
