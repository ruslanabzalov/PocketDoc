package com.ruslan.pocketdoc.searching.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Station;
import com.ruslan.pocketdoc.searching.BaseContract;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String EXTRA_STATION_ID = "station_id";
    private static final String EXTRA_STATION_NAME = "station_name";

    private BaseContract.BasePresenter mStationsPresenter;
    private StationsContract.Interactor mStationInteractor;

    private RecyclerView mStationsRecyclerView;
    private ProgressBar mStationsProgressBar;

    public static String getStationsFragmentResult(Intent data, String parameter) {
        switch (parameter) {
            case "id":
                return data.getStringExtra(EXTRA_STATION_ID);
            case "name":
                return data.getStringExtra(EXTRA_STATION_NAME);
            default:
                return null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_stations, container, false);
        mStationsRecyclerView = rootView.findViewById(R.id.stations_recycler_view);
        mStationsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mStationsRecyclerView.setLayoutManager(linearLayoutManager);
        mStationsProgressBar = rootView.findViewById(R.id.stations_progress_bar);
        mStationInteractor = new StationsInteractor();
        mStationsPresenter = new StationsPresenter(this, mStationInteractor);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mStationsPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStationsPresenter.onDestroy();
    }

    @Override
    public void showStationList(List<Station> stationList) {
        final StationsAdapter stationsAdapter =
                new StationsAdapter(stationList, this::setStationsFragmentResult);
        mStationsRecyclerView.setAdapter(stationsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                getString(R.string.load_error_toast) + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mStationsRecyclerView.setVisibility(View.GONE);
        mStationsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mStationsRecyclerView.setVisibility(View.VISIBLE);
        mStationsProgressBar.setVisibility(View.GONE);
    }

    private void setStationsFragmentResult(Station station) {
        final String stationId = station.getId();
        final String stationName = station.getName();
        final Intent data = new Intent();
        data.putExtra(EXTRA_STATION_ID, stationId);
        data.putExtra(EXTRA_STATION_NAME, stationName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
