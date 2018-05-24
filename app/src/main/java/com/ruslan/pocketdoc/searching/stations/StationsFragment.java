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
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.Station;
import com.ruslan.pocketdoc.data.StationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class StationsFragment extends Fragment {

    private static final String EXTRA_STATION_ID = "station_id";
    private static final String EXTRA_STATION_NAME = "station_name";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mStationsRecyclerView;

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
        final View view = inflater.inflate(R.layout.fragment_stations, container, false);
        mStationsRecyclerView = view.findViewById(R.id.stations_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mStationsRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStations();
    }

    private void loadStations() {
        final DocDocApi api = DocDocClient.getClient();
        final Call<StationList> stationsListCall = api.getStations(MOSCOW_ID);
        stationsListCall.enqueue(new Callback<StationList>() {
            @Override
            public void onResponse(@NonNull Call<StationList> call,
                                   @NonNull Response<StationList> response) {
                final StationList stationList = response.body();
                if (stationList != null) {
                    final StationsAdapter stationsAdapter =
                            new StationsAdapter(stationList.getStationList(),
                                    StationsFragment.this::setStationsFragmentResult);
                    mStationsRecyclerView.setAdapter(stationsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationList> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),
                        getString(R.string.load_error_toast) + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
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
