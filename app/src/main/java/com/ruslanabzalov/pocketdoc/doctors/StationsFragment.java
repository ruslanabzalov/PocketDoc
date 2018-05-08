package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class StationsFragment extends Fragment {

    private static final String EXTRA_STATION_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_id";
    private static final String EXTRA_STATION_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_name";

    private static final int MOSCOW_ID = 1;

    private RecyclerView mStationsRecyclerView;

    private List<Station> mStations = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Станции метро");
        DocDocApi api = DocDocClient.createClient();
        stationsCall(api, MOSCOW_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stations_list, container, false);
        mStationsRecyclerView = view.findViewById(R.id.stations_list_recycler_view);
        mStationsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void fragmentResult(String stationId, String stationName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_STATION_ID, stationId);
        data.putExtra(EXTRA_STATION_NAME, stationName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    private void stationsCall(DocDocApi api, int cityId) {
        Call<StationsList> stations = api.getStations(DocDocClient.AUTHORIZATION, cityId);
        stations.enqueue(new Callback<StationsList>() {
            @Override
            public void onResponse(@NonNull Call<StationsList> call,
                                   @NonNull Response<StationsList> response) {
                mStations = response.body().getStations();
                mStationsRecyclerView.setAdapter(new StationsAdapter(mStations));
            }

            @Override
            public void onFailure(@NonNull Call<StationsList> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),
                        getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private class StationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStationNameTextView;

        private Station mStation;

        private StationsHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            mStationNameTextView = itemView.findViewById(R.id.station_text_view);
        }

        private void bind(Station station) {
            mStation = station;
            mStationNameTextView.setText(mStation.getName());
        }

        @Override
        public void onClick(View v) {
            String stationId = mStation.getId();
            String stationName = mStation.getName();
            fragmentResult(stationId, stationName);
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
            View view = LayoutInflater.from(parent.getContext())
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
}
