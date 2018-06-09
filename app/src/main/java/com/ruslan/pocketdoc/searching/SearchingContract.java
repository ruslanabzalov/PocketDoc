package com.ruslan.pocketdoc.searching;

interface SearchingContract {

    interface View {

        void navigateToSpecs();

        void setSpecsButtonText(String specName);

        void navigateToStations();

        void setStationsButtonText(String stationName);

        void navigateToDoctors(String specId, String stationsId);

        void navigateToRecordsHistory();

        void disableDoctorsButton();

        void enableDoctorsButton();
    }

    interface Presenter {

        void detach();

        void onSpecsButtonClick();

        void onSelectSpec(String specName);

        void onStationsButtonClick();

        void onSelectStation(String stationName);

        void onDoctorsButtonClick(String specId, String stationId);

        void onRecordsHistoryMenuItemClick();

        void checkSelectedParams(String specId, String stationId);
    }
}
