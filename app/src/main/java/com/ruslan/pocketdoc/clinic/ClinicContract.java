package com.ruslan.pocketdoc.clinic;

import com.ruslan.pocketdoc.data.clinics.Clinic;

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
