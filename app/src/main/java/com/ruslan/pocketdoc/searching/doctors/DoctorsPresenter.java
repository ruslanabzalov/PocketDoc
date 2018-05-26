package com.ruslan.pocketdoc.searching.doctors;

import com.ruslan.pocketdoc.data.Doctor;

import java.util.List;

class DoctorsPresenter implements DoctorsContract.Presenter {

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
    public void onDestroy() {
        mDoctorsView = null;
    }

    @Override
    public void getDoctors() {
        mDoctorsInteractor.loadDoctors(mSpecialityId, mStationId,
                new DoctorsContract.Interactor.OnLoadFinishedListener() {
                    @Override
                    public void onSuccess(List<Doctor> doctorList) {
                        if (mDoctorsView != null) {
                            mDoctorsView.showDoctors(doctorList);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        if (mDoctorsView != null) {
                            mDoctorsView.showLoadErrorMessage(throwable);
                        }
                    }
                });
    }
}
