package com.ruslan.pocketdoc.searching.specialities;

import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.searching.BaseContract;

import java.util.List;

interface SpecialitiesContract {
    interface View extends BaseContract.BaseView {
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
