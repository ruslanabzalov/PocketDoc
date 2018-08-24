package com.ruslan.pocketdoc.specialities;

import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Класс, описывающий Presenter для взаимодействия с <code>SpecialitiesFragment</code>.
 */
public class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private static final String TAG = "SpecialitiesPresenter";

    private SpecialitiesContract.View mView;

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    SpecialitiesPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(SpecialitiesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mDisposable.dispose();
    }

    @Override
    public void loadSpecialities() {
        dispose(mDisposable);
        mView.showProgressBar();
        mDisposable = mRepository.getSpecialities(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> Log.i(TAG, "getSpecialities: onSubscribe()"))
                .doOnNext(specialities -> {
                    Log.i(TAG, "getSpecialities: onNext()");
                    Log.i(TAG, "Specialities loaded: " + specialities.size());
                    showList(specialities);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getSpecialities: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showError(throwable);
                })
                .doOnComplete(() -> Log.i(TAG, "getSpecialities: onComplete"))
                .subscribe();
    }

    @Override
    public void updateSpecialities(boolean isMenuRefreshing) {
        dispose(mDisposable);
        if (isMenuRefreshing) {
            mView.showProgressBar();
            forceUpdateSpecialities(true);
        } else {
            forceUpdateSpecialities(false);
        }
    }

    @Override
    public void chooseSpeciality(Speciality speciality) {
        mView.showStationsUi(speciality.getId());
    }

    /**
     * Метод для освобождения ресурсов.
     * @param disposable Объект типа <code>Disposable</code>, содержащий освобождаемые ресурсы.
     */
    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * Метод принудительного обновления списка специальностей.
     * @param isMenuRefreshing Значение типа <code>boolean</code>, указывающее на способ обновления.
     */
    private void forceUpdateSpecialities(boolean isMenuRefreshing) {
        mDisposable = mRepository.getSpecialities(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> Log.i(TAG, "getSpecialities: onSubscribe()"))
                .doOnNext(specialities -> {
                    Log.i(TAG, "getSpecialities: onNext()");
                    Log.i(TAG, "Specialities loaded: " + specialities.size());
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getSpecialities: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                })
                .doOnComplete(() -> Log.i(TAG, "getSpecialities: onComplete"))
                .subscribe(
                        specialities -> showUpdatedList(specialities, isMenuRefreshing),
                        throwable -> showRefreshingError(throwable, isMenuRefreshing)
                );
    }

    /**
     * Метод отображения полученного списка специальностей.
     * @param specialities Список специальностей.
     */
    private void showList(List<Speciality> specialities) {
        if (mView != null) {
            mView.showSpecialities(specialities);
            mView.hideProgressBar();
        }
    }

    /**
     * Метод отображения сообщения об ошибке при неудачном получении списка специальностей.
     * @param throwable Исключение, выброшенное при неудачном получении списка специальностей.
     */
    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            mView.hideProgressBar();
        }
    }

    /**
     * Метод отображения обновлённого списка специальностей.
     * @param specialities Список специальностей.
     * @param isMenuRefreshing Значение типа <code>boolean</code>, указывающее на способ обновления.
     */
    private void showUpdatedList(List<Speciality> specialities, boolean isMenuRefreshing) {
        if (mView != null) {
            mView.showSpecialities(specialities);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }

    /**
     * Метод отображения сообщения об ошибке при неудачном обновлении списка специальностей.
     * @param throwable Исключение, выброшенное при неудачном обновлении списка специальностей.
     * @param isMenuRefreshing Значение типа <code>boolean</code>, указывающее на способ обновления.
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
