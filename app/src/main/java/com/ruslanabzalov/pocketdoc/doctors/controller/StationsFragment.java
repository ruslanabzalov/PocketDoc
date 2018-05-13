package com.ruslanabzalov.pocketdoc.doctors.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocClient;
import com.ruslanabzalov.pocketdoc.doctors.model.Station;
import com.ruslanabzalov.pocketdoc.doctors.model.StationsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class StationsFragment extends Fragment {

    private static final String TAG = "StationsFragment";
    private static final String EXTRA_STATION_ID = "station_id";
    private static final String EXTRA_STATION_NAME = "station_name";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mStationsRecyclerView;

    private List<Station> mStations = new ArrayList<>();

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
        DocDocApi api = DocDocClient.getClient();
        stationsCall(api, MOSCOW_ID);
        return view;
    }

    private class StationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStationNameTextView;
        private TextView mLineNameTextView;

        private Station mStation;

        private StationsHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
            mLineNameTextView = itemView.findViewById(R.id.line_name_text_view);
        }

        private void bind(Station station) {
            mStation = station;
            mStationNameTextView.setText(mStation.getName());
            mLineNameTextView.setText(mStation.getLineName());
            String lineColor =
                    String.format(Locale.getDefault(), "#%s", mStation.getLineColor());
            mLineNameTextView.setTextColor(Color.parseColor(lineColor));
        }

        @Override
        public void onClick(View v) {
            String stationId = mStation.getId();
            String stationName = mStation.getName();
            setFragmentResult(stationId, stationName);
        }
    }

    private class StationsAdapter extends RecyclerView.Adapter<StationsHolder> {

        private List<Station> mStations;

        StationsAdapter(List<Station> stations) {
            mStations = stations;
        }

        @NonNull
        @Override
        public StationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_station, parent, false);
            return new StationsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StationsHolder stationsHolder, int position) {
            stationsHolder.bind(mStations.get(position));
        }

        @Override
        public int getItemCount() {
            return mStations.size();
        }
    }

    private void stationsCall(DocDocApi api, int cityId) {
        Call<StationsList> stations = api.getStations(DocDocClient.AUTHORIZATION, cityId);
        stations.enqueue(new Callback<StationsList>() {
            @Override
            public void onResponse(@NonNull Call<StationsList> call,
                                   @NonNull Response<StationsList> response) {
                StationsList stationsList = response.body();
                if (stationsList != null) {
                    mStations = stationsList.getStations();
                    StationsAdapter stationsAdapter = new StationsAdapter(mStations);
                    mStationsRecyclerView.setAdapter(stationsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationsList> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), getString(R.string.error_toast), Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, getString(R.string.error_toast), t);
            }
        });
    }

    private void setFragmentResult(String stationId, String stationName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_STATION_ID, stationId);
        data.putExtra(EXTRA_STATION_NAME, stationName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
