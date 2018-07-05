package com.ruslan.pocketdoc.doctor;

import com.ruslan.pocketdoc.data.doctors.Doctor;

public class DoctorPresenter implements DoctorContract.Presenter {

    private DoctorContract.View mView;

    @Override
    public void attachView(DoctorContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void displayDoctor(Doctor doctor) {
        mView.showDoctorInfo(doctor);
    }

    @Override
    public void onCreateRecordButtonClick() {
        mView.showNewRecordUi();
    }
}
