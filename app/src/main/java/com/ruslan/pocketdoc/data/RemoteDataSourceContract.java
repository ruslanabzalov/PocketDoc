package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorInfo;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Flowable;

interface RemoteDataSourceContract {

    Flowable<SpecialityList> getSpecialities();

    Flowable<StationList> getStations();

    Flowable<DoctorList> getDoctors(String specialityId, String stationId);

    Flowable<DoctorInfo> getDoctor(int doctorId);

    Flowable<ClinicList> getClinics();
}
