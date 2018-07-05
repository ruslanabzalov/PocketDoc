package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;

import io.reactivex.Flowable;
import io.reactivex.Observable;

interface RemoteDataSourceContract extends DataSourceContract {

    Flowable<SpecialityList> getSpecialities();

    Observable<DoctorList> getDoctors(String specialityId, String stationId);
}
