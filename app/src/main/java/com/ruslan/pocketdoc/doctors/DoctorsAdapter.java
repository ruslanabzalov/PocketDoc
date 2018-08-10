package com.ruslan.pocketdoc.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.ruslan.pocketdoc.StringUtils;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

/**
 * Класс, описывающий пользовательский RecyclerView Adapter.
 */
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_doctor, parent, false);
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

    /**
     * Вложенный класс, описывающий пользовательский RecyclerView ViewHolder.
     */
    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        private TextView mDoctorSpecialityTextView;
        private TextView mDoctorNameTextView;
        private RatingBar mDoctorRating;
        private TextView mDoctorExperienceTextView;
        private TextView mDoctorPriceTextView;

        private Doctor mDoctor;

        DoctorViewHolder(View view, RecyclerItemOnClickListener<Doctor> listener) {
            super(view);
            initViews();
            itemView.setOnClickListener((View v) -> listener.onRecyclerItemClickListener(mDoctor));
        }

        /**
         * Метод привязки данных врача к ViewHolder.
         * @param doctor Врач.
         */
        void bind(Doctor doctor) {
            mDoctor = doctor;
            mDoctorSpecialityTextView
                    .setText(StringUtils.getCorrectSpecialitiesString(mDoctor.getSpecialities()));
            mDoctorNameTextView.setText(mDoctor.getName());
            mDoctorRating.setRating(Float.parseFloat(mDoctor.getRating()));
            mDoctorExperienceTextView
                    .setText(StringUtils.getCorrectExperienceString(mDoctor.getExperience()));
            mDoctorPriceTextView.setText(StringUtils.getCorrectPriceString(mDoctor.getPrice()));
        }

        /**
         * Метод инициализации элементов View.
         */
        private void initViews() {
            mDoctorSpecialityTextView = itemView.findViewById(R.id.doctor_speciality_text_view);
            mDoctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
            mDoctorRating = itemView.findViewById(R.id.doctor_rating_bar);
            mDoctorExperienceTextView = itemView.findViewById(R.id.doctor_experience_text_view);
            mDoctorPriceTextView = itemView.findViewById(R.id.doctor_price_text_view);
        }
    }
}
