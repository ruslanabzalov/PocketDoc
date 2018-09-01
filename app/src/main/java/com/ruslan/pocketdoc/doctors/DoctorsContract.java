package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

interface DoctorsContract {

    interface View extends BaseContract.BaseView {

        void showDoctors(List<Doctor> doctorList);

        void showDoctorInfoUi(int doctorId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadDoctors(String specialityId, String stationId);

        void setDoctorsSchedules(List<Doctor> doctors, String preferredDate);

        void updateDoctors(String specialityId, String stationId, boolean isMenuRefreshing);

        void chooseDoctor(Doctor doctor);
    }
}
