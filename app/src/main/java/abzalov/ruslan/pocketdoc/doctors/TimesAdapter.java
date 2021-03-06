package abzalov.ruslan.pocketdoc.doctors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;

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
        return new TimesViewHolder(view, mDoctor, mOnCreateRecordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimesViewHolder holder, int position) {
        String startTime = mDoctor.getDaySchedules().get(position).getStartTime();
        startTime = startTime.substring(startTime.length() - 9, startTime.length() - 3);
        holder.mTimeButton.setText(startTime.trim());
    }

    @Override
    public int getItemCount() {
        return mDoctor.getDaySchedules().size();
    }

    static class TimesViewHolder extends RecyclerView.ViewHolder {

        private Button mTimeButton;

        private TimesViewHolder(View rootView, Doctor doctor, OnCreateRecordListener onCreateRecordListener) {
            super(rootView);
            mTimeButton = itemView.findViewById(R.id.time_button);
            mTimeButton.setOnClickListener(view ->
                    onCreateRecordListener.onCreateRecord(OnCreateRecordListener.SCHEDULE_BUTTON, doctor)
            );
        }
    }
}
