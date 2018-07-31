package com.ruslan.pocketdoc.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ruslan.pocketdoc.data.AppDatabase;
import com.ruslan.pocketdoc.data.LocalDataSource;
import com.ruslan.pocketdoc.data.RemoteDataSource;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "pocketdoc-db").build();
    }

    @Provides
    @Singleton
    RemoteDataSource provideRemoteDataSource() {
        return new RemoteDataSource();
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource() {
        return new LocalDataSource();
    }

    @Provides
    @Singleton
    Repository provideRepository() {
        return new Repository();
    }
}
