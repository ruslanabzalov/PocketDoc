package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

interface ClinicsContract {

    interface View {

        void startClinicsService();

        void addMarkers(List<Clinic> clinics);

        void showErrorDialog(Throwable throwable);

        void showClinicInfoUi(int clinicId);
    }

    interface Presenter {

        void attachView(View view);

        void detachView();

        void loadClinics();

        void getClinicsFromDb();

        void chooseClinic(int clinicId);
    }
}
