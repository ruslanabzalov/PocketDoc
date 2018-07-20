package com.ruslan.pocketdoc.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ruslan.pocketdoc.data.AppDatabase;
import com.ruslan.pocketdoc.data.LocalDataSource;
import com.ruslan.pocketdoc.data.RemoteDataSource;
import com.ruslan.pocketdoc.data.Repository;

import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModuleTest {

    @Provides
    @Singleton
    public AppDatabase provideDatabaseTest(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "pocketdoc-db").build();
    }

    @Provides
    @Singleton
    public ExecutorService provideExecutorTest() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    public Handler provideHandlerTest() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @Singleton
    public RemoteDataSource provideRemoteDataSourceTest() {
        return Mockito.mock(RemoteDataSource.class);
    }

    @Provides
    @Singleton
    public LocalDataSource provideLocalDataSourceTest() {
        return new LocalDataSource();
    }

    @Provides
    @Singleton
    public Repository provideRepository() {
        return new Repository();
    }
}
