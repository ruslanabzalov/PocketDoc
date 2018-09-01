package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

public interface SpecialitiesContract {

    interface View extends BaseContract.BaseView {

        void showSpecialities(List<Speciality> specialities);

        void showStationsUi(String specialityId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSpecialities();

        void updateSpecialities(boolean isMenuRefreshing);

        void chooseSpeciality(Speciality speciality);
    }
}
