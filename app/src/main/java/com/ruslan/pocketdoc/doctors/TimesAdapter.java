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

    private CreateRecordListener mCreateRecordListener;

    private Doctor mDoctor;

    TimesAdapter(Doctor doctor, CreateRecordListener createRecordListener) {
        mCreateRecordListener = createRecordListener;
        mDoctor = doctor;
    }

    @NonNull
    @Override
    public TimesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_time, parent, false);
        return new TimesViewHolder(view, mCreateRecordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimesViewHolder holder, int position) {
        holder.mTimeButton.setText("17:00");
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class TimesViewHolder extends RecyclerView.ViewHolder {

        private Button mTimeButton;

        private TimesViewHolder(View rootView, CreateRecordListener createRecordListener) {
            super(rootView);
            mTimeButton = itemView.findViewById(R.id.time_button);
            mTimeButton.setOnClickListener(view -> createRecordListener.onCreateRecordListener());
        }
    }
}
