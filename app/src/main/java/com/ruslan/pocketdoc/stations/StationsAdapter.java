package com.ruslan.pocketdoc.stations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;

import java.util.List;

class StationsAdapter extends RecyclerView.Adapter<StationViewHolder> {

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
}

class StationViewHolder extends RecyclerView.ViewHolder {

    private TextView mStationNameTextView;
    private TextView mLineNameTextView;

    private Station mStation;

    StationViewHolder(View view, RecyclerItemOnClickListener<Station> listener) {
        super(view);
        itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mStation));
        mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
        mLineNameTextView = itemView.findViewById(R.id.line_name_text_view);
    }

    void bind(Station station) {
        mStation = station;
        mStationNameTextView.setText(mStation.getName());
        mLineNameTextView.setText(mStation.getLineName());
    }
}
