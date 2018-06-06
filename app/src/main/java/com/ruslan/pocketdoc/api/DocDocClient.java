package com.ruslan.pocketdoc.api;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DocDocClient {

    private static final String USER_CREDENTIALS = "partner.13849:BIQWlAdw";
    private static final String AUTHORIZATION = "Basic " +
            new String(Base64.encode((USER_CREDENTIALS).getBytes(), Base64.NO_WRAP));

    private static DocDocApi sClient;

    private DocDocClient() {}

    public static DocDocApi getClient() {
        if (sClient == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(DocDocClient::createCustomInterceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://" + USER_CREDENTIALS + "@back.docdoc.ru/api/rest/1.0.6/json/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            sClient = retrofit.create(DocDocApi.class);
        }
        return sClient;
    }

    private static Response createCustomInterceptor(@NonNull Interceptor.Chain chain)
            throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", AUTHORIZATION)
                .build();
        return chain.proceed(request);
    }
}
