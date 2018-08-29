package com.ruslan.pocketdoc.doctors;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.ruslan.pocketdoc.StringUtils;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Класс, описывающий пользовательский RecyclerView Adapter.
 */
public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private RecyclerItemOnClickListener<Doctor> mListener;
    private OnCreateRecordListener mOnCreateRecordListener;

    private List<Doctor> mDoctors;

    DoctorsAdapter(List<Doctor> doctors, RecyclerItemOnClickListener<Doctor> listener,
                   OnCreateRecordListener onCreateRecordListener) {
        mDoctors = doctors;
        mListener = listener;
        mOnCreateRecordListener = onCreateRecordListener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_doctor, parent, false);
        return new DoctorViewHolder(view, mListener, mOnCreateRecordListener);
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

        private OnCreateRecordListener mOnCreateRecordListener;

        private CircleImageView mDoctorPhotoImageView;
        private TextView mDoctorSpecialityTextView;
        private TextView mDoctorNameTextView;
        private RatingBar mDoctorRating;
        private TextView mDoctorExperienceTextView;
        private TextView mDoctorPriceTextView;
        private Button mCreateRecordButton;
        private RecyclerView mScheduleRecyclerView;

        @Inject
        Picasso mPicasso;

        private Doctor mDoctor;

        DoctorViewHolder(View view, RecyclerItemOnClickListener<Doctor> listener,
                         OnCreateRecordListener onCreateRecordListener) {
            super(view);
            App.getComponent().inject(this);
            initViews();
            mOnCreateRecordListener = onCreateRecordListener;
            itemView.setOnClickListener((View v) -> listener.onRecyclerItemClickListener(mDoctor));
        }

        /**
         * Метод привязки данных врача к ViewHolder.
         * @param doctor Врач.
         */
        void bind(Doctor doctor) {
            mDoctor = doctor;
            mPicasso.load(mDoctor.getPhotoUrl()).into(mDoctorPhotoImageView);
            mDoctorSpecialityTextView
                    .setText(StringUtils.getCorrectSpecialitiesString(mDoctor.getSpecialities()));
            mDoctorNameTextView.setText(mDoctor.getName());
            mDoctorRating.setRating(Float.parseFloat(mDoctor.getRating()));
            mDoctorExperienceTextView
                    .setText(StringUtils.getCorrectExperienceString(mDoctor.getExperience()));
            mDoctorPriceTextView.setText(StringUtils.getCorrectPriceString(mDoctor.getPrice()));
            checkDoctorsSchedule(doctor);
        }

        /**
         * Метод инициализации элементов View.
         */
        private void initViews() {
            mDoctorPhotoImageView = itemView.findViewById(R.id.doctor_photo_circle_image_view);
            mDoctorSpecialityTextView = itemView.findViewById(R.id.doctor_speciality_text_view);
            mDoctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
            mDoctorRating = itemView.findViewById(R.id.doctor_rating_bar);
            mDoctorExperienceTextView = itemView.findViewById(R.id.doctor_experience_text_view);
            mDoctorPriceTextView = itemView.findViewById(R.id.doctor_price_text_view);
            mCreateRecordButton = itemView.findViewById(R.id.doctor_create_record_button);
            mCreateRecordButton.setOnClickListener(view ->
                    mOnCreateRecordListener
                            .onCreateRecord(OnCreateRecordListener.SIMPLE_RECORD_BUTTON));
            mScheduleRecyclerView = itemView.findViewById(R.id.schedulers_recycler_view);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mScheduleRecyclerView.setLayoutManager(linearLayoutManager);
        }

        /**
         * Метод проверки наличия расписания у врача.
         * @param doctor Врач.
         */
        private void checkDoctorsSchedule(@NonNull Doctor doctor) {
            if (doctor.getSlotList() == null) {
                if (mScheduleRecyclerView.getVisibility() != View.GONE) {
                    mScheduleRecyclerView.setVisibility(View.GONE);
                }
                if (mCreateRecordButton.getVisibility() == View.GONE) {
                    mCreateRecordButton.setVisibility(View.VISIBLE);
                }
            } else {
                if (doctor.getDaySchedules().size() == 0) {
                    if (mScheduleRecyclerView.getVisibility() != View.GONE) {
                        mScheduleRecyclerView.setVisibility(View.GONE);
                    }
                    if (mCreateRecordButton.getVisibility() == View.GONE) {
                        mCreateRecordButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (mCreateRecordButton.getVisibility() != View.GONE) {
                        mCreateRecordButton.setVisibility(View.GONE);
                    }
                    if (mScheduleRecyclerView.getVisibility() == View.GONE) {
                        mScheduleRecyclerView.setVisibility(View.VISIBLE);
                    }
                    mScheduleRecyclerView.setAdapter(
                            new TimesAdapter(doctor, mOnCreateRecordListener)
                    );
                }
            }
        }
    }
}
