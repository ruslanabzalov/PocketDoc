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
import com.ruslanabzalov.pocketdoc.docdoc.DocDocService;

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

    /**
     * Константа, хранящая идентификатор города Москвы.
     */
    private static final int MOSCOW_ID = 1;

    private RecyclerView mStationsRecyclerView;

    private List<Station> mStations = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Станции метро");
        DocDocApi api = DocDocService.getClient();
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

    /**
     * Вспомогательный метод, устанавливающий адаптер RecyclerView.
     */
    private void setupAdapter() {
        if (isAdded()) {
            mStationsRecyclerView.setAdapter(new StationsAdapter(mStations));
        }
    }

    private void fragmentResult(String stationId, String stationName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_STATION_ID, stationId);
        data.putExtra(EXTRA_STATION_NAME, stationName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    /**
     * Вспомогательный метод для получения списка станций метро в определённом городе.
     * @param api API-интерфейс сервиса DocDoc.
     * @param cityId Идентификатор города.
     */
    private void stationsCall(DocDocApi api, int cityId) {
        Call<StationsList> stations = api.getStations(DocDocService.AUTHORIZATION, cityId);
        stations.enqueue(new Callback<StationsList>() {
            @Override
            public void onResponse(@NonNull Call<StationsList> call,
                                   @NonNull Response<StationsList> response) {
                mStations = response.body().getStations();
                setupAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<StationsList> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),
                        getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    /**
     * Класс, описывающий холдер RecyclerView.
     */
    private class StationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStationNameTextView;

        private Station mStation;

        private StationsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_station, parent, false));
            itemView.setOnClickListener(this);
            mStationNameTextView = itemView.findViewById(R.id.station_text_view);
        }

        public void bind(Station station) {
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

    /**
     * Класс, описывающий адаптер RecyclerView.
     */
    private class StationsAdapter extends RecyclerView.Adapter<StationsHolder> {

        private List<Station> mStations;

        StationsAdapter(List<Station> stations) {
            mStations = stations;
        }

        @NonNull
        @Override
        public StationsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StationsHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull StationsHolder stationsHolder, int position) {
            Station station = mStations.get(position);
            stationsHolder.bind(station);
        }

        /**
         * Метод, возвращающий кол-во элементов в RecyclerView.
         * @return Кол-во элементов в RecyclerView.
         */
        @Override
        public int getItemCount() {
            return mStations.size();
        }
    }
}
