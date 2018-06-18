package com.ruslan.pocketdoc.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ruslan.pocketdoc.data.AppDatabase;
import com.ruslan.pocketdoc.data.LocalDataSourceImpl;
import com.ruslan.pocketdoc.data.RemoteDataSourceImpl;
import com.ruslan.pocketdoc.data.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "pocketdoc-db").build();
    }

    @Provides
    @Singleton
    public ExecutorService provideExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    public Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @Singleton
    public RemoteDataSourceImpl provideRemoteDataSource() {
        return new RemoteDataSourceImpl();
    }

    @Provides
    @Singleton
    public LocalDataSourceImpl provideLocalDataSource() {
        return new LocalDataSourceImpl();
    }

    @Provides
    @Singleton
    public Repository provideRepository() {
        return new Repository();
    }
}
