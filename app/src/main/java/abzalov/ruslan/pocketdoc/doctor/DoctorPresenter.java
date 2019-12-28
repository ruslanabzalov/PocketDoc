package abzalov.ruslan.pocketdoc.doctor;

import android.util.Log;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.data.Repository;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoctorPresenter implements DoctorContract.Presenter {

    private static final String TAG = "DoctorsPresenter";

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
        dispose(mDisposable);
        mView.showProgressBar();
        mDisposable = mRepository.getDoctorInfo(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getDoctorInfo: onSubscribe()"))
                .doOnSuccess(doctor -> {
                    Log.i(TAG, "getDoctorInfo: onNext()");
                    showDoctorInfo(doctor);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctorInfo: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showError(throwable);
                })
                .subscribe();
    }

    @Override
    public void updateDoctorInfo(int doctorId, boolean isMenuRefreshing) {
        dispose(mDisposable);
        if (isMenuRefreshing) {
            mView.showProgressBar();
            forceUpdateDoctorInfo(doctorId, true);
        } else {
            forceUpdateDoctorInfo(doctorId, false);
        }
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void forceUpdateDoctorInfo(int doctorId, boolean isMenuRefreshing) {
        mDisposable = mRepository.getDoctorInfo(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getDoctorInfo: onSubscribe()"))
                .doOnSuccess(doctor -> {
                    Log.i(TAG, "getDoctorInfo: onNext()");
                    showUpdatedDoctorInfo(doctor, isMenuRefreshing);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctorInfo: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showRefreshingError(throwable, isMenuRefreshing);
                })
                .subscribe();
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

    private void showUpdatedDoctorInfo(Doctor doctor, boolean isMenuRefreshing) {
        if (mView != null) {
            if (isMenuRefreshing) {
            } else {
                mView.hideRefreshing();
            }
            mView.hideProgressBar();
            mView.showDoctorInfo(doctor);
        }
    }

    private void showRefreshingError(Throwable throwable, boolean isMenuRefreshing) {
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
