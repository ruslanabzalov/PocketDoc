package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

interface StationsContract {

    interface View extends BaseContract.BaseView {

        void hideRefreshing();

        void showStations(List<Station> stations);

        void showDoctorsListUi(String stationId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadStations();

        void updateStations(boolean isMenuRefreshing);

        void chooseStation(Station station);
    }
}
