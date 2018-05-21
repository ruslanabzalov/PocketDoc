package com.ruslan.pocketdoc.searching.doctors;

import com.ruslan.pocketdoc.data.Doctor;

import java.util.List;

public interface DoctorsInteractor {

    interface OnLoadFinishedListener {
        void onSuccess(List<Doctor> doctors);
        void onFailure(Throwable throwable);
    }

    void loadDoctors(OnLoadFinishedListener onLoadFinishedListener, String specId, String stationId);
}
