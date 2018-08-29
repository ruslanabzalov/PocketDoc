package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.api.CreateRecordRequest;
import com.ruslan.pocketdoc.api.CreateRecordResponse;
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Класс, реализующий интерфейс работы с API DocDoc.
 */
public class RemoteDataSource implements RemoteDataSourceContract {

    @Inject
    DocDocApi mApi;

    public RemoteDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mApi.getSpecialities()
                .map(SpecialityList::getSpecialities);
    }

    @Override
    public Flowable<List<Station>> getStations() {
        return mApi.getStations()
                .map(StationList::getStations);
    }

    @Override
    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mApi.getDoctors(specialityId, stationId)
                .map(DoctorList::getDoctors);
    }

    @Override
    public Single<Doctor> getDoctor(int doctorId) {
        return mApi.getDoctor(doctorId)
                .map(doctorInfo -> doctorInfo.getDoctor().get(0));
    }

    @Override
    public Flowable<List<Clinic>> getClinics(int start, int count) {
        return mApi.getClinics(start, count)
                .map(ClinicList::getClinics);
    }

    @Override
    public Single<CreateRecordResponse> createRecord(CreateRecordRequest recordRequest) {
        return mApi.createRecord(recordRequest);
    }
}
