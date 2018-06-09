package com.ruslan.pocketdoc.searching;

class SearchingPresenter implements SearchingContract.Presenter {

    private SearchingContract.View mView;

    SearchingPresenter(SearchingContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void onSpecsButtonClick() {
        mView.navigateToSpecs();
    }

    @Override
    public void onSelectSpec(String specName) {
        mView.setSpecsButtonText(specName);
    }

    @Override
    public void onStationsButtonClick() {
        mView.navigateToStations();
    }

    @Override
    public void onSelectStation(String stationName) {
        mView.setStationsButtonText(stationName);
    }

    @Override
    public void onDoctorsButtonClick(String specId, String stationId) {
        mView.navigateToDoctors(specId, stationId);
    }

    @Override
    public void onRecordsHistoryMenuItemClick() {
        mView.navigateToRecordsHistory();
    }

    @Override
    public void checkSelectedParams(String specId, String stationId) {
        if (specId != null && stationId != null) {
            mView.enableDoctorsButton();
        } else {
            mView.disableDoctorsButton();
        }
    }
}
