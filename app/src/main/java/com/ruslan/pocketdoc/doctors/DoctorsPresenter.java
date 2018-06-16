package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

class DoctorsPresenter implements BaseContract.BasePresenter {

    private DoctorsContract.View mDoctorsView;
    private Repository mRepository;

    private String mSpecialityId;
    private String mStationId;

    DoctorsPresenter(DoctorsContract.View view, String specialityId, String stationId) {
        mDoctorsView = view;
        //mRepository = Repository.getInstance();
        mSpecialityId = specialityId;
        mStationId = stationId;
    }

    @Override
    public void start() {
        if (mDoctorsView != null) {
            mDoctorsView.showProgressBar();
//            mRepository.getDoctors(mSpecialityId, mStationId, new DataSource.OnLoadFinishedListener<Doctor>() {
//                @Override
//                public void onSuccess(List<Doctor> items) {
//                    mDoctorsView.showDoctors(items);
//                    mDoctorsView.hideProgressBar();
//                }
//
//                @Override
//                public void onFailure(Throwable throwable) {
//                    mDoctorsView.showLoadErrorMessage(throwable);
//                    mDoctorsView.hideProgressBar();
//                }
//            });
        }
    }

    @Override
    public void stop() {
        mDoctorsView = null;
    }
}
