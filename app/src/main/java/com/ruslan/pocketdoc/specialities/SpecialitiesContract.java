package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

interface SpecialitiesContract {

    interface View {

        void showLoadErrorMessage(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void showSpecialities(List<Speciality> specialityList);

        void navigateToStationsList(String specialityId);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void onSpecialityClick(Speciality speciality);
    }

    interface Interactor {

        interface OnLoadFinishedListener {

            void onSuccess(List<Speciality> specialityList);

            void onFailure(Throwable throwable);
        }

        void loadSpecialities(OnLoadFinishedListener listener);
    }
}
