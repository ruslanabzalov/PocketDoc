package com.ruslan.pocketdoc.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;

class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.TimesViewHolder> {

    private OnCreateRecordListener mOnCreateRecordListener;

    private Doctor mDoctor;

    TimesAdapter(Doctor doctor, OnCreateRecordListener onCreateRecordListener) {
        mOnCreateRecordListener = onCreateRecordListener;
        mDoctor = doctor;
    }

    @NonNull
    @Override
    public TimesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_time, parent, false);
        return new TimesViewHolder(view, mOnCreateRecordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimesViewHolder holder, int position) {
        holder.mTimeButton.setText("18:00");
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class TimesViewHolder extends RecyclerView.ViewHolder {

        private Button mTimeButton;

        private TimesViewHolder(View rootView, OnCreateRecordListener onCreateRecordListener) {
            super(rootView);
            mTimeButton = itemView.findViewById(R.id.time_button);
            mTimeButton.setOnClickListener(view -> onCreateRecordListener.onCreateRecord());
        }
    }
}
