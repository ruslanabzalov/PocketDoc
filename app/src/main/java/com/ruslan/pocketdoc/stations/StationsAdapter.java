package com.ruslan.pocketdoc.stations;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_station, parent, false);
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
        private ImageView mLineIndicator;

        private Station mStation;

        StationViewHolder(View view, RecyclerItemOnClickListener<Station> listener) {
            super(view);
            itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mStation));
            mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
            mLineIndicator = itemView.findViewById(R.id.metro_indicator);
        }

        void bind(Station station) {
            mStation = station;
            String stationName = mStation.getName();
            mStationNameTextView.setText(stationName);
            Drawable lineIndicatorDrawable = mLineIndicator.getDrawable();
            int lineColor = Color.parseColor("#" + mStation.getLineColor());
            // TODO: Узнать побольше о классе PorterDuffColorFilter!
            lineIndicatorDrawable.setColorFilter(new PorterDuffColorFilter(lineColor, PorterDuff.Mode.SRC_IN));
            mLineIndicator.setBackground(lineIndicatorDrawable);
        }
    }
}
