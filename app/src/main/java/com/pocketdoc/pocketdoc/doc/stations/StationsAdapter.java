package com.pocketdoc.pocketdoc.doc.stations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pocketdoc.pocketdoc.R;
import com.pocketdoc.pocketdoc.data.Station;
import com.pocketdoc.pocketdoc.doc.RecyclerItemOnClickListener;

import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsViewHolder> {

    private RecyclerItemOnClickListener<Station> mStationRecyclerItemOnClickListener;

    private List<Station> mStations;

    StationsAdapter(List<Station> stations,
                    RecyclerItemOnClickListener<Station> stationRecyclerItemOnClickListener) {
        mStations = stations;
        mStationRecyclerItemOnClickListener = stationRecyclerItemOnClickListener;
    }

    @NonNull
    @Override
    public StationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_station, parent, false);
        return new StationsViewHolder(view, mStationRecyclerItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StationsViewHolder holder, int position) {
        holder.bind(mStations.get(position));
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }
}

class StationsViewHolder extends RecyclerView.ViewHolder {

    private TextView mStationNameTextView;
    private TextView mLineNameTextView;

    private Station mStation;

    StationsViewHolder(View view,
                       RecyclerItemOnClickListener<Station> stationRecyclerItemOnClickListener) {
        super(view);
        mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
        mLineNameTextView = itemView.findViewById(R.id.line_name_text_view);
        itemView.setOnClickListener(v ->
                stationRecyclerItemOnClickListener.onRecyclerItemClickListener(mStation));
    }

    void bind(Station station) {
        mStation = station;
        mStationNameTextView.setText(mStation.getName());
        mLineNameTextView.setText(mStation.getLineName());
    }
}
