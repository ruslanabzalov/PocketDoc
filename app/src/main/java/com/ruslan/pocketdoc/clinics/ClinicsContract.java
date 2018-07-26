package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

interface ClinicsContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void showSuccessLoadingMessage();

        void showErrorDialog(Throwable throwable);

        void showClinics(List<Clinic> clinics);

        void showClinicInfoUi(int clinicId);
    }

    interface Presenter {

        void attachView(View view);

        void detachView();

        void loadClinics();

        void updateClinics();

        void getOnlyClinics();

        void getOnlyDiagnostics();

        void chooseClinic(Clinic clinic);
    }
}
