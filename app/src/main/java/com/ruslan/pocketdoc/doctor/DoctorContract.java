package com.ruslan.pocketdoc.doctor;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

interface DoctorContract {

    interface View extends BaseContract.BaseView {

        void showDoctorInfo(Doctor doctor);

        void showNewRecordUi();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadDoctorInfo(int doctorId);

        void updateDoctorInfo(int doctorId, boolean isMenuRefreshing);

        void onCreateRecordButtonClick();
    }
}
