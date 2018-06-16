package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

interface StationsContract {

    interface View {

        void showLoadErrorMessage(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void showStationList(List<Station> stationList);

        void navigateToDoctorsList(String stationId);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void onStationClick(Station station);
    }
}
