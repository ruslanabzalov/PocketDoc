package com.ruslan.pocketdoc.stations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> {

    private RecyclerItemOnClickListener<Station> mListener;

    private List<Station> mStations;

    StationsAdapter(List<Station> stations, RecyclerItemOnClickListener<Station> listener) {
        mStations = stations;
        mListener = listener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_station, parent, false);
        return new StationViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Station station = mStations.get(position);
        holder.bind(station);
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    void updateDataSet(List<Station> stations) {
        mStations = stations;
        notifyDataSetChanged();
    }

    static class StationViewHolder extends RecyclerView.ViewHolder {

        private TextView mStationNameTextView;
        private TextView mLineNameTextView;
        private ImageView mLineIndicator;

        private Station mStation;

        StationViewHolder(View view, RecyclerItemOnClickListener<Station> listener) {
            super(view);
            itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mStation));
            mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
            mLineNameTextView = itemView.findViewById(R.id.line_name_text_view);
            mLineIndicator = itemView.findViewById(R.id.metro_indicator);
        }

        void bind(Station station) {
            mStation = station;

            String stationName =
                    String.format("%s %s",
                            itemView.getResources().getString(R.string.station_name_preview),
                            mStation.getName());

            String stationLineName;
            if (mStation.getLineName().contains("кольцо")) {
                stationLineName = mStation.getLineName();
            } else {
                stationLineName =
                        String.format("%s %s",
                                mStation.getLineName(),
                                itemView.getResources().getString(R.string.line_name_preview).toLowerCase());
            }
            mStationNameTextView.setText(stationName);

            mLineNameTextView.setText(stationLineName);

            // TODO: Изменить цвет индикатора линии метро.

        }
    }
}
