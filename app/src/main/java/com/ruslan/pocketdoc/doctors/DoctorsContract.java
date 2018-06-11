package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

interface DoctorsContract {

    interface View {
        void showLoadErrorMessage(Throwable throwable);
        void showProgressBar();
        void hideProgressBar();
        void showDoctors(List<Doctor> doctorList);
    }

    interface Interactor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Doctor> doctorList);
            void onFailure(Throwable throwable);
        }

        void loadDoctors(String specialityId, String stationId, OnLoadFinishedListener listener);
    }
}
