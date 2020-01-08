package abzalov.ruslan.pocketdoc.doctors;

import java.util.List;

import abzalov.ruslan.pocketdoc.BaseContract;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;

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
