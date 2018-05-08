package com.ruslanabzalov.pocketdoc.docdoc;

import android.util.Base64;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс, описывающий работу с сервисом DocDoc.
 */
public class DocDocClient {

    private static final String LOGIN = "partner.13849";
    private static final String PASSWORD = "BIQWlAdw";

    /**
     * Строка для авторизации.
     */
    public static final String AUTHORIZATION = "Basic " +
            new String(Base64.encode((LOGIN + ":" + PASSWORD).getBytes(), Base64.NO_WRAP));

    private DocDocClient() {}

    public static DocDocApi createClient() {
        // Создание пользовательского экземпляра OkHttpClient.
        final OkHttpClient httpClient = new OkHttpClient.Builder()
                // Timeout после 60 секунд.
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        // Создание пользовательского экземпляра Retrofit.
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://" +
                        LOGIN + ":" + PASSWORD +
                        "@back.docdoc.ru/api/rest/1.0.6/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(DocDocApi.class);
    }
}
