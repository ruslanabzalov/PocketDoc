package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalDataSource implements LocalDataSourceContract {

    @Inject
    AppDatabase mDatabase;

    public LocalDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mDatabase.specialityDao().getAllSpecialities();
    }

    @Override
    public Completable saveSpecialities(List<Speciality> specialities) {
        return Completable.fromAction(() -> mDatabase.specialityDao().insertSpecialities(specialities));
    }

    @Override
    public Flowable<List<Station>> getStations() {
        return mDatabase.stationDao().getAllStations();
    }

    @Override
    public Completable saveStations(List<Station> stations) {
        return Completable.fromAction(() -> mDatabase.stationDao().insertStations(stations));
    }

    @Override
    public Flowable<List<Clinic>> getClinics() {
        return mDatabase.clinicsDao().getAllClinics();
    }

    @Override
    public Completable saveClinics(List<Clinic> clinics) {
        return Completable.fromAction(() -> mDatabase.clinicsDao().insertClinics(clinics));
    }
}
