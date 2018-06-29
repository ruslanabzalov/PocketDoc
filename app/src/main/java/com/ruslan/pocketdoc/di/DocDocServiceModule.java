package com.ruslan.pocketdoc.di;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.ruslan.pocketdoc.api.DocDocApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DocDocServiceModule {

    private String USER_CREDENTIALS = "partner.13849:BIQWlAdw";
    private String AUTHORIZATION = "Basic " +
            new String(Base64.encode((USER_CREDENTIALS).getBytes(), Base64.NO_WRAP));

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(this::customInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://" + USER_CREDENTIALS + "@back.docdoc.ru/api/rest/1.0.6/json/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public DocDocApi provideDocDocService(Retrofit retrofit) {
        return retrofit.create(DocDocApi.class);
    }

    private Response customInterceptor(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", AUTHORIZATION)
                .build();
        return chain.proceed(request);
    }
}
