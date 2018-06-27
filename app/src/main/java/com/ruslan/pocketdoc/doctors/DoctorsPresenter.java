package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

import javax.inject.Inject;

public class DoctorsPresenter implements DoctorsContract.Presenter {

    private DoctorsContract.View mView;

    @Inject
    Repository mRepository;

    public DoctorsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(DoctorsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadDoctors(String specialityId, String stationId) {
        mView.showProgressBar();
        mRepository.getDoctors(specialityId, stationId, new DataSource.OnLoadFinishedListener<Doctor>() {
            @Override
            public void onSuccess(List<Doctor> doctors) {
                if (mView != null) {
                    mView.showDoctors(doctors);
                    mView.hideProgressBar();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mView != null) {
                    mView.showErrorMessage(throwable);
                    mView.hideProgressBar();
                }
            }
        });
    }

    @Override
    public void updateDoctors(String specialityId, String stationId, boolean isMenuUpdate) {
        if (isMenuUpdate) {
            mView.showProgressBar();
            mRepository.getDoctors(specialityId, stationId, new DataSource.OnLoadFinishedListener<Doctor>() {
                @Override
                public void onSuccess(List<Doctor> doctors) {
                    if (mView != null) {
                        mView.showDoctors(doctors);
                        mView.hideProgressBar();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if (mView != null) {
                        mView.showErrorMessage(throwable);
                        mView.hideProgressBar();
                    }
                }
            });
        } else {
            mRepository.getDoctors(specialityId, stationId, new DataSource.OnLoadFinishedListener<Doctor>() {
                @Override
                public void onSuccess(List<Doctor> doctors) {
                    if (mView != null) {
                        mView.showDoctors(doctors);
                        mView.hideRefreshing();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if (mView != null) {
                        mView.showErrorMessage(throwable);
                        mView.hideRefreshing();
                    }
                }
            });
        }
    }

    @Override
    public void onDoctorClick(Doctor doctor) {
        mView.showDoctorInfoUi(doctor);
    }
}
