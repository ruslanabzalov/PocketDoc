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

    ClinicsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(ClinicsContract.View view) {
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
    public void loadClinics() {
        mDisposable = mRepository.getClinicsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((integer, throwable) -> {
                    if (mView != null) {
                        if (throwable != null) {
                            mView.showErrorDialog(throwable);
                        }
                        if (integer == 0) {
                            mView.startClinicsService();
                        }
                    }
                });
    }

    @Override
    public void getClinicsFromDb() {
        mDisposable = mRepository.getClinicsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clinics -> {
                            if (mView != null) {
                                if (clinics.size() != 0) {
                                    mView.addMarkers(clinics);
                                }
                            }
                        },
                        throwable -> {
                            if (mView != null) {
                                mView.showErrorDialog(throwable);
                            }
                        }
                );
    }

    @Override
    public void chooseClinic(int clinicId) {
        mView.showClinicInfoUi(clinicId);
    }
}
