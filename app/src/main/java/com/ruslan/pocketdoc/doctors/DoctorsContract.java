package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

interface DoctorsContract {

    interface View extends BaseContract.BaseView {

        void hideRefreshing();

        void showDoctors(List<Doctor> doctorList);

        void showDoctorInfoUi(Doctor doctor);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadDoctors(String specialityId, String stationId);

        void updateDoctors(String specialityId, String stationId, boolean isMenuUpdate);

        void onDoctorClick(Doctor doctor);
    }
}
