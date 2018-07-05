package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

interface LocalDataSourceContract extends DataSourceContract {

    Flowable<List<Speciality>> getSpecialities();

    Completable saveSpecialities(List<Speciality> specialities);

    void saveStations(List<Station> stations);

    void saveClinics(List<Clinic> clinics);
}
