package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClinicsPresenter implements ClinicsContract.Presenter {

    private Disposable mDisposable;

    @Inject
    Repository mRepository;

    private ClinicsContract.View mView;

    public ClinicsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(ClinicsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mDisposable.dispose();
    }

    @Override
    public void loadClinics() {
        mDisposable = mRepository.getClinics(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clinics -> {
                            if (mView != null) {
                                mView.showClinics(clinics);
                            }
                        },
                        throwable -> {
                            if (mView != null) {
                                mView.showErrorMessage(throwable);
                            }
                        }
                );
    }

    @Override
    public void updateClinics() {
        mDisposable = mRepository.getClinics(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clinics -> {
                            if (mView != null) {
                                mView.showClinics(clinics);
                            }
                        },
                        throwable -> {
                            if (mView != null) {
                                mView.showErrorMessage(throwable);
                            }
                        }
                );
    }
}
