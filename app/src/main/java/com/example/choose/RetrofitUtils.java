package com.example.choose;

import com.example.choose.api.PostController;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    private Retrofit retrofit;

    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit(null);
        }
        return retrofit;
    }

    public void createRetrofit(String session) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://choose.teheidoma.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .followRedirects(false)
                        .addInterceptor(chain -> {
                            Request.Builder builder = chain
                                    .request()
                                    .newBuilder();
                            if (session != null) {
                                builder.addHeader("Cookie", "JSESSIONID=" + session);
                            }

                            return chain.proceed(builder.build());
                        }).build())
                .build();
    }
}