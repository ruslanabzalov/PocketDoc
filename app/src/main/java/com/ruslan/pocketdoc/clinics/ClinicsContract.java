package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

interface ClinicsContract {

    interface View extends BaseContract.BaseView {

        void showClinics(List<Clinic> clinics);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadClinics();

        void updateClinics();
    }
}
