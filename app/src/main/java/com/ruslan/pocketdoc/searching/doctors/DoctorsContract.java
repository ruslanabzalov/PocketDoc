package com.ruslan.pocketdoc.searching.doctors;

import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.searching.BaseContract;

import java.util.List;

public interface DoctorsContract {

    interface View extends BaseContract.BaseView {
        void showDoctors(List<Doctor> doctorList);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void getDoctors();
    }

    interface Interactor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Doctor> doctorList);
            void onFailure(Throwable throwable);
        }

        void loadDoctors(String specialityId, String stationId, OnLoadFinishedListener listener);
    }
}
