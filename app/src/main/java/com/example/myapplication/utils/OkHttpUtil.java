package com.example.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {
    private Handler handler=new Handler();
    private final OkHttpClient client;
    private OkHttpUtil(){
        client=new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private static class SingleHolder {
        private static final OkHttpUtil UTIL=new OkHttpUtil();
    }

    public static OkHttpUtil getInstance() {
        return SingleHolder.UTIL;
    }
    public boolean is(Context context){
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null){
            return info.isConnected();
        }else {
            return false;
        }
    }
    public void doGet(String url,OkCallBack callBack){
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.okError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.okSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private OkCallBack callBack;

    public void setCallBack(OkCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OkCallBack{
        void okSuccess(String json);
        void okError(String error);
    }
}
