package com.ruslan.pocketdoc.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;
import java.util.Locale;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private RecyclerItemOnClickListener<Doctor> mListener;

    private List<Doctor> mDoctors;

    DoctorsAdapter(List<Doctor> doctors, RecyclerItemOnClickListener<Doctor> listener) {
        mDoctors = doctors;
        mListener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctor, parent, false);
        return new DoctorViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = mDoctors.get(position);
        holder.bind(doctor);
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    void updateDataSet(List<Doctor> doctors) {
        mDoctors = doctors;
        notifyDataSetChanged();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        private TextView mDoctorSpecialityTextView;
        private TextView mDoctorNameTextView;
        private RatingBar mDoctorRating;
        private TextView mDoctorExperienceTextView;
        private TextView mDoctorPriceTextView;

        private Doctor mDoctor;

        DoctorViewHolder(View view, RecyclerItemOnClickListener<Doctor> listener) {
            super(view);
            App.getComponent().inject(this);
            itemView.setOnClickListener((View v) -> listener.onRecyclerItemClickListener(mDoctor));
            mDoctorSpecialityTextView = itemView.findViewById(R.id.doctor_speciality_text_view);
            mDoctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
            mDoctorRating = itemView.findViewById(R.id.doctor_rating_bar);
            mDoctorExperienceTextView = itemView.findViewById(R.id.doctor_experience_text_view);
            mDoctorPriceTextView = itemView.findViewById(R.id.doctor_price_text_view);
        }

        void bind(Doctor doctor) {
            mDoctor = doctor;
            if (mDoctor.getSpecialities().size() >= 1) {
                StringBuilder doctorSpecialities = new StringBuilder(mDoctor.getSpecialities().get(0).getName());
                for (int i = 1; i < mDoctor.getSpecialities().size(); i++) {
                    doctorSpecialities.append(", ").append(mDoctor.getSpecialities().get(i).getName());
                }
                mDoctorSpecialityTextView.setText(doctorSpecialities.toString());
            } else {
                mDoctorSpecialityTextView.setText(mDoctor.getSpecialities().get(0).getName());
            }
            mDoctorNameTextView.setText(mDoctor.getName());
            float doctorRating = Float.parseFloat(mDoctor.getRating());
            mDoctorRating.setRating(doctorRating);
            mDoctorExperienceTextView.setText(mDoctor.getExperience() + " лет");
            String doctorPrice = String.format(Locale.getDefault(), "%d\u20bd", mDoctor.getPrice());
            mDoctorPriceTextView.setText(doctorPrice);
        }
    }
}
