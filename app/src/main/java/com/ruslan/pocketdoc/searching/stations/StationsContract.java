package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.Station;
import com.ruslan.pocketdoc.searching.BaseContract;

import java.util.List;

interface StationsContract {

    interface View extends BaseContract.BaseView {
        void showStationList(List<Station> stationList);
    }

    interface Interactor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Station> stationList);
            void onFailure(Throwable t);
        }

        void loadStations(OnLoadFinishedListener listener);
    }
}
