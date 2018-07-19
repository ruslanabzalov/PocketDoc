package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class RemoteDataSource implements RemoteDataSourceContract {

    @Inject
    DocDocApi mApi;

    public RemoteDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<SpecialityList> getSpecialities() {
        return mApi.getSpecialities();
    }

    @Override
    public Flowable<StationList> getStations() {
        return mApi.getStations();
    }

    @Override
    public Flowable<DoctorList> getDoctors(String specialityId, String stationId) {
        return mApi.getDoctors(specialityId, stationId);
    }

    @Override
    public Flowable<Doctor> getDoctor(int doctorId) {
        return mApi.getDoctor(doctorId);
    }

    @Override
    public Flowable<ClinicList> getClinics() {
        return mApi.getClinics();
    }
}
