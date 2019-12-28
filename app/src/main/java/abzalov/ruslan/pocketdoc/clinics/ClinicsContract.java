package abzalov.ruslan.pocketdoc.clinics;

import abzalov.ruslan.pocketdoc.BaseContract;
import abzalov.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

interface ClinicsContract {

    interface View extends BaseContract.BaseView {

        void scheduleClinicsJobService();

        void addMarkers(List<Clinic> clinics);

        void showClinicsInCurrentArea();

        void showClinicInfoUi(int clinicId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getClinicsCount();

        void getAllClinicsFromDb();

        void getOnlyClinicsFromDb();

        void getOnlyDiagnosticsFromDb();
    }
}
