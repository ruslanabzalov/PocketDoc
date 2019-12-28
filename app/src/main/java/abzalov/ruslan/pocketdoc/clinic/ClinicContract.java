package abzalov.ruslan.pocketdoc.clinic;

import abzalov.ruslan.pocketdoc.data.clinics.Clinic;

public interface ClinicContract {

    interface View {

        void showClinicInfo(Clinic clinic);
    }

    interface Presenter {

        void attachView(View view);

        void detachView();

        void loadClinicInfo(int clinicId);
    }
}
