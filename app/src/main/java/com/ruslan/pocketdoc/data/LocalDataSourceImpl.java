package com.ruslan.pocketdoc.data;

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

    private LocalDataSourceImpl() {
        mDatabase = AppDatabaseImpl.getInstance();
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static LocalDataSourceImpl getInstance() {
        if (sLocalDatabase == null) {
            sLocalDatabase = new LocalDataSourceImpl();
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
            List<Station> stations = mDatabase.stationDao().getAllStations();
            if (stations.size() == 0) {
                mainHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mainHandler.post(() -> listener.onSuccess(stations));
            }
        });
    }

    @Override
    public void saveStations(List<Station> stations) {
        mExecutorService.execute(() -> mDatabase.stationDao().insertStations(stations));
    }
}
