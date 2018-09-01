package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.api.CreateRecordRequestSchedule;
import com.ruslan.pocketdoc.api.CreateRecordResponse;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

interface RemoteDataSourceContract {

    Flowable<List<Speciality>> getSpecialities();

    Flowable<List<Station>> getStations();

    Flowable<List<Doctor>> getDoctors(String specialityId, String stationId);

    Single<Doctor> getDoctor(int doctorId);

    Flowable<List<Clinic>> getClinics(int start, int count);

    Single<CreateRecordResponse> createRecord(CreateRecordRequestSchedule createRecordRequestSchedule);
}
