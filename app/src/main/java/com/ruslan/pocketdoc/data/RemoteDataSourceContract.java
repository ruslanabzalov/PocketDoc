package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.doctors.DoctorList;

import io.reactivex.Observable;

interface RemoteDataSourceContract extends DataSourceContract {

    Observable<DoctorList> getDoctors(String specialityId, String stationId);
}
