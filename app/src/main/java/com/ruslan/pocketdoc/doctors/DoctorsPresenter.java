package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoctorsPresenter implements DoctorsContract.Presenter {

    private DoctorsContract.View mView;

    @Inject
    Repository mRepository;

    public DoctorsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(DoctorsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadDoctors(String specialityId, String stationId) {
        mRepository.getDoctors(specialityId, stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(doctorList -> mView.showDoctors(doctorList.getDoctors()));
    }

    @Override
    public void updateDoctors(String specialityId, String stationId, boolean isMenuUpdate) {
        mRepository.getDoctors(specialityId, stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(doctorList -> mView.showDoctors(doctorList.getDoctors()));
    }

    @Override
    public void chooseDoctor(Doctor doctor) {
        mView.showDoctorInfoUi(doctor);
    }
}
