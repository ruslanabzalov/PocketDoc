package com.ruslan.pocketdoc.doctor;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

interface DoctorContract {

    interface View extends BaseContract.BaseView {

        void showDoctorInfo(Doctor doctor);

        void showNewRecordUi();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void displayDoctor(Doctor doctor);

        void onCreateRecordButtonClick();
    }
}
