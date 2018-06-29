package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Repository {

    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    LocalDataSource mLocalDataSource;

    public Repository() {
        App.getComponent().inject(this);
    }

    public Observable<SpecialityList> getSpecialities() {
        return mRemoteDataSource.getSpecialities();
    }

    public Observable<StationList> getStations() {
        return mRemoteDataSource.getStations();
    }

    public Observable<DoctorList> getDoctors(String specialityId, String stationId) {
        return mRemoteDataSource.getDoctors(specialityId, stationId);
    }

    public Observable<ClinicList> getClinics() {
        return mRemoteDataSource.getClinics();
    }
}
