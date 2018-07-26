package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoctorsPresenter implements DoctorsContract.Presenter {

    private DoctorsContract.View mView;

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    DoctorsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(DoctorsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        // Может быть null при смене конфигурации в следующем фрагменте.
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void loadDoctors(String specialityId, String stationId) {
        mView.showProgressBar();
        mDisposable = mRepository.getDoctors(specialityId, stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showList, this::showError);
    }

    @Override
    public void updateDoctors(String specialityId, String stationId, boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mDisposable = mRepository.getDoctors(specialityId, stationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            doctors -> showUpdatedList(doctors, true),
                            throwable -> showRefreshingError(throwable, true)
                    );
        } else {
            mDisposable = mRepository.getDoctors(specialityId, stationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            doctors -> showUpdatedList(doctors, false),
                            throwable -> showRefreshingError(throwable, false)
                    );
        }
    }

    @Override
    public void chooseDoctor(Doctor doctor) {
        mView.showDoctorInfoUi(doctor.getId());
    }

    private void showList(List<Doctor> doctors) {
        if (mView != null) {
            mView.showDoctors(doctors);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            mView.hideProgressBar();
        }
    }

    private void showUpdatedList(List<Doctor> doctors, boolean isMenuRefreshing) {
        if (mView != null) {
            mView.showDoctors(doctors);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }

    private void showRefreshingError(Throwable throwable, boolean isMenuRefreshing) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }
}
