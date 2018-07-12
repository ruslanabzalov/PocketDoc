package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

interface SpecialitiesContract {

    interface View extends BaseContract.BaseView {

        void hideRefreshing();

        void showSpecialities(List<Speciality> specialities);

        void showStationListUi(String specialityId);

        void showRecordsHistoryListUi();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSpecialities();

        void updateSpecialities(boolean isMenuRefreshing);

        void chooseSpeciality(Speciality speciality);
    }
}
