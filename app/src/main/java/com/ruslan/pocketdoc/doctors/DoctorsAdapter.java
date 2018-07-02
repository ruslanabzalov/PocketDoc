package com.ruslan.pocketdoc.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

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

    static class DoctorViewHolder extends RecyclerView.ViewHolder {

        private ImageView mDoctorPhotoImageView;
        private TextView mDoctorNameTextView;
        private TextView mDoctorRatingTextView;
        private TextView mDoctorExperienceTextView;

        private Doctor mDoctor;

        DoctorViewHolder(View view, RecyclerItemOnClickListener<Doctor> listener) {
            super(view);
            itemView.setOnClickListener((View v) -> listener.onRecyclerItemClickListener(mDoctor));
            mDoctorPhotoImageView = itemView.findViewById(R.id.doctor_photo_image_view);
            mDoctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
            mDoctorRatingTextView = itemView.findViewById(R.id.doctor_rating_text_view);
            mDoctorExperienceTextView = itemView.findViewById(R.id.doctor_experience_text_view);
        }

        void bind(Doctor doctor) {
            mDoctor = doctor;
            Picasso.get()
                    .load(mDoctor.getPhotoUrl())
                    .into(mDoctorPhotoImageView);
            mDoctorNameTextView.setText("Имя врача: " + mDoctor.getName());
            mDoctorRatingTextView.setText("Рейтинг врача " + mDoctor.getRating() + "/5");
            mDoctorExperienceTextView.setText("Стаж работы: " + mDoctor.getExperience());
        }
    }
}
