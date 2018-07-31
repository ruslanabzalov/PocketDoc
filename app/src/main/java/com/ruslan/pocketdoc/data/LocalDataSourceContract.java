package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

interface LocalDataSourceContract {

    Flowable<List<Speciality>> getSpecialities();

    Completable saveSpecialities(List<Speciality> specialities);

    Flowable<List<Station>> getStations();

    Completable saveStations(List<Station> stations);

    Single<Integer> countClinics();

    Flowable<List<Clinic>> getClinics();

    Flowable<List<Clinic>> getOnlyClinics();

    Flowable<List<Clinic>> getOnlyDiagnostics();

    Completable saveClinics(List<Clinic> clinics);
}
