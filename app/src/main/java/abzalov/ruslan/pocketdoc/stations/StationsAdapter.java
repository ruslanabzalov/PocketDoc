package abzalov.ruslan.pocketdoc.stations;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.RecyclerItemOnClickListener;
import abzalov.ruslan.pocketdoc.data.stations.Station;

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

        private ImageView mLineIndicator;
        private TextView mStationNameTextView;

        private Station mStation;

        StationViewHolder(View view, RecyclerItemOnClickListener<Station> listener) {
            super(view);
            mStationNameTextView = itemView.findViewById(R.id.station_name_text_view);
            mLineIndicator = itemView.findViewById(R.id.metro_indicator);
            itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mStation));
        }

        void bind(Station station) {
            mStation = station;
            String stationName = mStation.getName();
            mStationNameTextView.setText(stationName);
            Drawable lineIndicatorDrawable = mLineIndicator.getDrawable();
            int lineColor = Color.parseColor("#" + mStation.getLineColor());
            lineIndicatorDrawable
                    .setColorFilter(new PorterDuffColorFilter(lineColor, PorterDuff.Mode.SRC_IN));
            mLineIndicator.setBackground(lineIndicatorDrawable);
        }
    }
}
