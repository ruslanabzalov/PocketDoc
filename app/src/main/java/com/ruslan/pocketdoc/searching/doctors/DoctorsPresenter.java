package com.ruslan.pocketdoc.searching.doctors;

import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.searching.BasePresenter;
import com.ruslan.pocketdoc.searching.BaseView;

import java.util.List;

public class DoctorsPresenter implements BasePresenter {

    private BaseView<Doctor> mDoctorsView;
    private DoctorsInteractor mDoctorsInteractor;

    private String mDocsSpecId;
    private String mDocsStationId;

    DoctorsPresenter(BaseView<Doctor> doctorsView, DoctorsInteractor doctorsInteractor,
                     String docsSpecId, String docsStationId) {
        mDoctorsView = doctorsView;
        mDoctorsInteractor = doctorsInteractor;
        mDocsSpecId = docsSpecId;
        mDocsStationId = docsStationId;
    }

    @Override
    public void onResume() {
        mDoctorsInteractor.loadDoctors(new DoctorsInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Doctor> doctors) {
                if (mDoctorsView != null) {
                    mDoctorsView.showItems(doctors);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mDoctorsView != null) {
                    mDoctorsView.showErrorMessage(throwable);
                }
            }
        }, mDocsSpecId, mDocsStationId);
    }

    @Override
    public void onDestroy() {
        mDoctorsView = null;
    }
}
