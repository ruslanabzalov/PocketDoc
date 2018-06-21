package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

import javax.inject.Inject;

public class DoctorsPresenter implements DoctorsContract.Presenter {

    private DoctorsContract.View mDoctorsView;

    @Inject
    Repository mRepository;

    private String mSpecialityId;
    private String mStationId;

    public DoctorsPresenter(String specialityId, String stationId) {
        mSpecialityId = specialityId;
        mStationId = stationId;
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(DoctorsContract.View view) {
        mDoctorsView = view;
    }

    @Override
    public void detach() {
        mDoctorsView = null;
    }

    @Override
    public void loadDoctors() {
        mDoctorsView.showProgressBar();
        mRepository.getDoctors(mSpecialityId, mStationId, new DataSource.OnLoadFinishedListener<Doctor>() {
            @Override
            public void onSuccess(List<Doctor> items) {
                mDoctorsView.showDoctors(items);
                mDoctorsView.hideProgressBar();
            }

            @Override
            public void onFailure(Throwable throwable) {
                mDoctorsView.showErrorMessage(throwable);
                mDoctorsView.hideProgressBar();
            }
        });

    }
}
