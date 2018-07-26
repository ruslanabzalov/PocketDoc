package com.ruslan.pocketdoc.doctor;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoctorPresenter implements DoctorContract.Presenter {

    private DoctorContract.View mView;

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    @Override
    public void attachView(DoctorContract.View view) {
        mView = view;
        App.getComponent().inject(this);
    }

    @Override
    public void detachView() {
        mView = null;
        mDisposable.dispose();
    }

    @Override
    public void loadDoctorInfo(int doctorId) {
        mView.showProgressBar();
        mDisposable = mRepository.getDoctorInfo(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showDoctorInfo, this::showError);
    }

    @Override
    public void updateDoctorInfo(int doctorId, boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mDisposable = getDoctorInfo(doctorId, true);
        } else {
            mDisposable = getDoctorInfo(doctorId, false);
        }
    }

    @Override
    public void onCreateRecordButtonClick() {
        mView.showNewRecordUi();
    }

    private void showDoctorInfo(Doctor doctor) {
        if (mView != null) {
            mView.showDoctorInfo(doctor);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
        }
    }

    private Disposable getDoctorInfo(int doctorId, boolean isMenuRefreshing) {
        return mRepository.getDoctorInfo(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        doctor -> showUpdatedDoctorInfo(doctor, isMenuRefreshing),
                        throwable -> showUpdateError(throwable, isMenuRefreshing)
                );
    }

    private void showUpdatedDoctorInfo(Doctor doctor, boolean isMenuRefreshing) {
        if (mView != null) {
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
            mView.showDoctorInfo(doctor);
        }
    }

    private void showUpdateError(Throwable throwable, boolean isMenuRefreshing) {
        if (mView != null) {
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
            mView.showErrorDialog(throwable);
        }
    }
}
