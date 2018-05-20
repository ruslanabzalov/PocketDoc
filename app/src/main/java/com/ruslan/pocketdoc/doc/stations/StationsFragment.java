package com.ruslan.pocketdoc.doc.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Station;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class StationsFragment extends Fragment implements StationsContract.MvpView {

    private static final String TAG = "StationsFragment";
    private static final String EXTRA_STATION_ID = "station_id";
    private static final String EXTRA_STATION_NAME = "station_name";

    private RecyclerView mStationsRecyclerView;

    private StationsMvpPresenter mStationsPresenter;

    public static String getData(Intent data, String param) {
        return (param.equals("id"))
                ? data.getStringExtra(EXTRA_STATION_ID)
                : data.getStringExtra(EXTRA_STATION_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        mStationsRecyclerView = view.findViewById(R.id.stations_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mStationsRecyclerView.setLayoutManager(linearLayoutManager);
        mStationsPresenter = new StationsMvpPresenter(this, new StationsMvpInteractor());
        mStationsPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStationsPresenter.onDestroy();
    }

    @Override
    public void showStations(List<Station> stations) {
        StationsAdapter stationsAdapter = new StationsAdapter(stations, this::setFragmentResult);
        mStationsRecyclerView.setAdapter(stationsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Ошибка получения данных от сервера: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private void setFragmentResult(Station station) {
        String stationId = station.getId();
        String stationName = station.getName();
        Intent data = new Intent();
        data.putExtra(EXTRA_STATION_ID, stationId);
        data.putExtra(EXTRA_STATION_NAME, stationName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
