package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

interface ClinicsContract {

    interface View extends BaseContract.BaseView {

        void showSuccessLoadingMessage();

        void showClinics(List<Clinic> clinics);

        void showClinicInfoUi(int clinicId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadClinics();

        void updateClinics();

        void getOnlyClinics();

        void getOnlyDiagnostics();

        void chooseClinic(Clinic clinic);
    }
}
