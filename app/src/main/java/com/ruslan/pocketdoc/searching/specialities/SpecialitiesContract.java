package com.ruslan.pocketdoc.searching.specialities;

import com.ruslan.pocketdoc.data.Speciality;

import java.util.List;

interface SpecialitiesContract {
    interface View {
        void showLoadErrorMessage(Throwable throwable);
        void showProgressBar();
        void hideProgressBar();
        void showSpecialities(List<Speciality> specialityList);
    }

    interface Interactor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Speciality> specialityList);
            void onFailure(Throwable throwable);
        }

        void loadSpecialities(OnLoadFinishedListener listener);
    }
}
