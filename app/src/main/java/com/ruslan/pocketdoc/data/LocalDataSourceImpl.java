package com.ruslan.pocketdoc.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSourceImpl implements LocalDataSource {

    private static LocalDataSourceImpl sLocalDatabase;

    private AppDatabase mDatabase;
    private ExecutorService mExecutorService;

    private LocalDataSourceImpl(Context context) {
        mDatabase = AppDatabase.getInstance(context);
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static LocalDataSourceImpl getInstance(Context context) {
        if (sLocalDatabase == null) {
            sLocalDatabase = new LocalDataSourceImpl(context);
        }
        return sLocalDatabase;
    }

    @Override
    public void getSpecialities(OnLoadFinishedListener<Speciality> listener) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mExecutorService.execute(() -> {
            List<Speciality> specialities = mDatabase.specialityDao().getAllSpecialities();
            if (specialities.size() == 0) {
                mainHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mainHandler.post(() -> listener.onSuccess(specialities));
            }
        });
    }

    @Override
    public void saveSpecialities(List<Speciality> specialities) {
        mExecutorService.execute(() -> mDatabase.specialityDao().insertSpecialities(specialities));
    }

    @Override
    public void getStations(OnLoadFinishedListener<Station> listener) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mExecutorService.execute(() -> {
            int size = mDatabase.stationDao().countAll();
            if (size == 0) {
                mainHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mainHandler.post(() -> {
                    List<Station> stations = mDatabase.stationDao().getAllStations();
                    listener.onSuccess(stations);
                });
            }
        });
    }

    @Override
    public void saveStations(List<Station> stations) {
        mExecutorService.execute(() -> mDatabase.stationDao().insertStations(stations));
    }
}
