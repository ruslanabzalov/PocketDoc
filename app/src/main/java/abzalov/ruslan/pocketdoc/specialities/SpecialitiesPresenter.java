package abzalov.ruslan.pocketdoc.specialities;

import android.util.Log;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.data.Repository;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        if (mDisposable != null) {
            mDisposable.dispose();
        }
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

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void forceUpdateSpecialities(boolean isMenuRefreshing) {
        mDisposable = mRepository.getSpecialities(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> Log.i(TAG, "getSpecialities: onSubscribe()"))
                .doOnNext(specialities -> {
                    Log.i(TAG, "getSpecialities: onNext()");
                    Log.i(TAG, "Specialities loaded: " + specialities.size());
                    showUpdatedList(specialities, isMenuRefreshing);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getSpecialities: onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showRefreshingError(throwable, isMenuRefreshing);
                })
                .doOnComplete(() -> Log.i(TAG, "getSpecialities: onComplete"))
                .subscribe();
    }

    private void showList(List<Speciality> specialities) {
        if (mView != null) {
            mView.showSpecialities(specialities);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            mView.hideProgressBar();
        }
    }

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
