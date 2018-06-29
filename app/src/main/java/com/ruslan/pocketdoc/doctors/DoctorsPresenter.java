package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        mRepository.getDoctors(specialityId, stationId, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DoctorList>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(DoctorList doctorList) {
                        if (mView != null) {
                            mView.showDoctors(doctorList.getDoctors());
                            mView.hideProgressBar();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.showErrorMessage(e);
                            mView.hideProgressBar();
                        }
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void updateDoctors(String specialityId, String stationId, boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mRepository.getDoctors(specialityId, stationId, true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DoctorList>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(DoctorList doctorList) {
                            if (mView != null) {
                                mView.showDoctors(doctorList.getDoctors());
                                mView.hideProgressBar();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mView != null) {
                                mView.showErrorMessage(e);
                                mView.hideProgressBar();
                            }
                        }

                        @Override
                        public void onComplete() {}
                    });
        } else {
            mRepository.getDoctors(specialityId, stationId, true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DoctorList>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(DoctorList doctorList) {
                            if (mView != null) {
                                mView.showDoctors(doctorList.getDoctors());
                                mView.hideRefreshing();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mView != null) {
                                mView.showErrorMessage(e);
                                mView.hideRefreshing();
                            }
                        }

                        @Override
                        public void onComplete() {}
                    });
        }
    }

    @Override
    public void chooseDoctor(Doctor doctor) {
        mView.showDoctorInfoUi(doctor);
    }
}
