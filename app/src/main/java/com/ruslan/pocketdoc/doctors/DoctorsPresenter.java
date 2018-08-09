package com.ruslan.pocketdoc.doctors;

import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Класс, описывающий Presenter для взаимодействия с фрагментом DoctorsFragment.
 */
public class DoctorsPresenter implements DoctorsContract.Presenter {

    private static final String TAG = "DoctorsPresenter";

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
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void loadDoctors(String specialityId, String stationId) {
        dispose(mDisposable);
        mView.showProgressBar();
        mDisposable = mRepository.getDoctors(specialityId, stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getDoctors: onSubscribe()"))
                .doOnNext(doctors -> {
                    Log.i(TAG, "getDoctors: onNext()");
                    Log.i(TAG, "Doctors loaded: " + doctors.size());
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctors: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                })
                .doOnComplete(() -> Log.i(TAG, "getDoctors: onComplete"))
                .subscribe(this::showList, this::showError);
    }

    @Override
    public void updateDoctors(String specialityId, String stationId, boolean isMenuRefreshing) {
        dispose(mDisposable);
        if (isMenuRefreshing) {
            mView.showProgressBar();
            forceUpdateDoctors(specialityId, stationId, true);
        } else {
            forceUpdateDoctors(specialityId, stationId, false);
        }
    }

    @Override
    public void chooseDoctor(Doctor doctor) {
        mView.showDoctorInfoUi(doctor.getId());
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
     * Метод принудительного обновления списка врачей.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
    private void forceUpdateDoctors(String specialityId, String stationId,
                                    boolean isMenuRefreshing) {
        mDisposable = mRepository.getDoctors(specialityId, stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getDoctors: onSubscribe()"))
                .doOnNext(doctors -> {
                    Log.i(TAG, "getDoctors: onNext()");
                    Log.i(TAG, "Doctors loaded: " + doctors.size());
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getDoctors: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                })
                .doOnComplete(() -> Log.i(TAG, "getDoctors: onComplete"))
                .subscribe(
                        doctors -> showUpdatedList(doctors, isMenuRefreshing),
                        throwable -> showRefreshingError(throwable, isMenuRefreshing));
    }

    /**
     * Метод отображения полученного списка врачей.
     * @param doctors Список врачей.
     */
    private void showList(List<Doctor> doctors) {
        if (mView != null) {
            mView.showDoctors(doctors);
            mView.hideProgressBar();
        }
    }

    /**
     * Метод отображения сообщения об ошибке при неудачном получении списка врачей.
     * @param throwable Выброшенное исключение.
     */
    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            mView.hideProgressBar();
        }
    }

    /**
     * Метод отображения обновлённого списка врачей.
     * @param doctors Список врачей.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
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

    /**
     * Метод отображения сообщения об ошибке при неудачном обновлении списка врачей.
     * @param throwable Выброшенное исключение.
     * @param isMenuRefreshing Флаг, указывающий на способ обновления.
     */
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
