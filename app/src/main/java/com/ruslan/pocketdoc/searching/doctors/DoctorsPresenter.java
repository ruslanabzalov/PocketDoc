package com.ruslan.pocketdoc.searching.doctors;

import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.BaseContract;

import java.util.List;

class DoctorsPresenter implements BaseContract.BasePresenter {

    private DoctorsContract.View mDoctorsView;
    private DoctorsContract.Interactor mDoctorsInteractor;

    private String mSpecialityId;
    private String mStationId;

    DoctorsPresenter(DoctorsContract.View view, DoctorsContract.Interactor interactor,
                     String specialityId, String stationId) {
        mDoctorsView = view;
        mDoctorsInteractor = interactor;
        mSpecialityId = specialityId;
        mStationId = stationId;
    }

    @Override
    public void start() {
        if (mDoctorsView != null) {
            mDoctorsView.showProgressBar();
            mDoctorsInteractor.loadDoctors(mSpecialityId, mStationId,
                    new DoctorsContract.Interactor.OnLoadFinishedListener() {
                        @Override
                        public void onSuccess(List<Doctor> doctorList) {
                            mDoctorsView.showDoctors(doctorList);
                            mDoctorsView.hideProgressBar();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            mDoctorsView.showLoadErrorMessage(throwable);
                            mDoctorsView.hideProgressBar();
                        }
                    });
        }
    }

    @Override
    public void stop() {
        mDoctorsView = null;
    }
}
