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
        mDisposable = mRepository.getDoctorInfo(doctorId)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onCreateRecordButtonClick() {
        mView.showNewRecordUi();
    }
}
