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
}
