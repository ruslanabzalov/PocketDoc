package com.ruslan.pocketdoc.data;

import android.os.Handler;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class LocalDataSourceImpl implements LocalDataSource {

    @Inject
    AppDatabase mDatabase;

    @Inject
    ExecutorService mExecutorService;

    @Inject
    Handler mHandler;

    public LocalDataSourceImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public void getSpecialities(OnLoadFinishedListener<Speciality> listener) {
        mExecutorService.execute(() -> {
            List<Speciality> specialities = mDatabase.specialityDao().getAllSpecialities();
            if (specialities.size() == 0) {
                mHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mHandler.post(() -> listener.onSuccess(specialities));
            }
        });
    }

    @Override
    public void saveSpecialities(List<Speciality> specialities) {
        mExecutorService.execute(() -> mDatabase.specialityDao().insertSpecialities(specialities));
    }

    @Override
    public void getStations(OnLoadFinishedListener<Station> listener) {
        mExecutorService.execute(() -> {
            List<Station> stations = mDatabase.stationDao().getAllStations();
            if (stations.size() == 0) {
                mHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mHandler.post(() -> listener.onSuccess(stations));
            }
        });
    }

    @Override
    public void saveStations(List<Station> stations) {
        mExecutorService.execute(() -> mDatabase.stationDao().insertStations(stations));
    }

    @Override
    public void getClinics(OnLoadFinishedListener<Clinic> listener) {
        mExecutorService.execute(() -> {
            List<Clinic> clinics = mDatabase.clinicsDao().getAllClinics();
            if (clinics.size() == 0) {
                mHandler.post(() -> listener.onFailure(new Throwable()));
            } else {
                mHandler.post(() -> listener.onSuccess(clinics));
            }
        });
    }

    @Override
    public void saveClinics(List<Clinic> clinics) {
        mExecutorService.execute(() -> mDatabase.clinicsDao().insertClinics(clinics));
    }
}
