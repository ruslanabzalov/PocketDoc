package com.ruslan.pocketdoc.doctor;

import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Класс, описывающий Presenter для взаимодействия с фрагментом DoctorFragment.
 */
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
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctorInfo: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                })
                .subscribe(this::showDoctorInfo, this::showError);
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

    /**
     * Метод освобождения ресурсов Disposable.
     * @param disposable Объект, содержащий освобождаемые ресурсы.
     */
    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * Метод принудительного обновления подробной информации о враче.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
    private void forceUpdateDoctorInfo(int doctorId, boolean isMenuRefreshing) {
        mDisposable = mRepository.getDoctorInfo(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getDoctorInfo: onSubscribe()"))
                .doOnSuccess(doctor -> {
                    Log.i(TAG, "getDoctorInfo: onNext()");
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctorInfo: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                })
                .subscribe(
                        doctor -> showUpdatedDoctorInfo(doctor, isMenuRefreshing),
                        throwable -> showRefreshingError(throwable, isMenuRefreshing));
    }

    /**
     * Метод отображения полученной подробной информации о враче.
     * @param doctor Врач.
     */
    private void showDoctorInfo(Doctor doctor) {
        if (mView != null) {
            mView.showDoctorInfo(doctor);
            mView.hideProgressBar();
        }
    }

    /**
     * Метод отображения сообщения об ошибке при неудачном получении подробной информации о враче.
     * @param throwable Выброшенное исключение.
     */
    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
        }
    }

    /**
     * Метод отображения обновлённой подробной информации о враче.
     * @param doctor Врач.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
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

    /**
     * Метод отображения сообщения об ошибке при неудачном обновлении списка врачей.
     * @param throwable Выброшенное исключение.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
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
