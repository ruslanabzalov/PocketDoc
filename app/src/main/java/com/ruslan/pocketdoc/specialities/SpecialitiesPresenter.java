package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

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
        mView.showProgressBar();
        mDisposable = mRepository.getSpecialities(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showList, this::showError);
    }

    @Override
    public void updateSpecialities(boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mDisposable = mRepository.getSpecialities(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            specialities -> showUpdatedList(specialities, true),
                            throwable -> showRefreshingError(throwable, true)
                    );
        } else {
            mDisposable = mRepository.getSpecialities(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            specialities -> showUpdatedList(specialities, false),
                            throwable -> showRefreshingError(throwable, false)
                    );
        }
    }

    @Override
    public void chooseSpeciality(Speciality speciality) {
        mView.showStationListUi(speciality.getId());
    }

    private void showList(List<Speciality> specialities) {
        if (mView != null) {
            mView.showSpecialities(specialities);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorMessage(throwable);
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
            mView.showErrorMessage(throwable);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }
}
