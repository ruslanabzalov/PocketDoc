package abzalov.ruslan.pocketdoc.doctors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.RecyclerItemOnClickListener;
import abzalov.ruslan.pocketdoc.StringUtils;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

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
