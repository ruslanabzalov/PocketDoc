package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

interface LocalDataSourceContract {

    Flowable<List<Speciality>> getSpecialities();

    void saveSpecialities(List<Speciality> specialities);

    Flowable<List<Station>> getStations();

    void saveStations(List<Station> stations);

    Single<Integer> countClinics();

    Flowable<List<Clinic>> getAllClinics();

    Flowable<List<Clinic>> getOnlyClinics(String isDiagnostic);

    Flowable<List<Clinic>> getOnlyDiagnostics(String isDiagnostic);

    Single<Clinic> getClinicById(int id);

    void saveClinics(List<Clinic> clinics);
}
