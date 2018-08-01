package com.ruslan.pocketdoc.di;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.get();
    }

    @Provides
    @Singleton
    ExecutorService provideExecutor() {
        return Executors.newCachedThreadPool();
    }
}
