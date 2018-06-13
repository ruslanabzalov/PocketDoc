package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.doctors.Doctor;

public interface RemoteDataSource extends DataSource {

    void getDoctors(String specialityId, String stationId, OnLoadFinishedListener<Doctor> listener);
}
