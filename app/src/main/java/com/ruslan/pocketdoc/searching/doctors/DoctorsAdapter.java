package com.ruslan.pocketdoc.searching.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.searching.RecyclerItemOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorViewHolder> {

    private RecyclerItemOnClickListener<Doctor> mDoctorRecyclerItemOnClickListener;

    private List<Doctor> mDoctors;

    DoctorsAdapter(List<Doctor> doctors,
                   RecyclerItemOnClickListener<Doctor> doctorRecyclerItemOnClickListener) {
        mDoctors = doctors;
        mDoctorRecyclerItemOnClickListener = doctorRecyclerItemOnClickListener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_doctor, parent, false);
        return new DoctorViewHolder(view, mDoctorRecyclerItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        holder.bind(mDoctors.get(position));
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }
}

class DoctorViewHolder extends RecyclerView.ViewHolder {

    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorRatingTextView;

    private Doctor mDoctor;

    DoctorViewHolder(View view,
                     RecyclerItemOnClickListener<Doctor> doctorRecyclerItemOnClickListener) {
        super(view);
        itemView.setOnClickListener((View v)
                -> doctorRecyclerItemOnClickListener.onRecyclerItemClickListener(mDoctor));
        mDoctorPhotoImageView = itemView.findViewById(R.id.doctor_photo_image_view);
        mDoctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
        mDoctorRatingTextView = itemView.findViewById(R.id.doctor_rating_text_view);
    }

    void bind(Doctor doctor) {
        mDoctor = doctor;
        Picasso.get()
                .load(mDoctor.getPhotoUrl())
                .into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(mDoctor.getName());
        mDoctorRatingTextView.setText(mDoctor.getRating());
    }
}
