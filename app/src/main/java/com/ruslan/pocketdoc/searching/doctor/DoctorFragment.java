package com.ruslan.pocketdoc.searching.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.searching.records.NewRecordActivity;
import com.squareup.picasso.Picasso;

public class DoctorFragment extends Fragment {

    private static final String ARG_DOCTOR = "doctor";

    private Doctor mDoctor;

    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorRatingTextView;
    private Button mEnrollDoctorButton;

    public static Fragment newInstance(Doctor doctor) {
        final Bundle args = new Bundle();
        args.putSerializable(ARG_DOCTOR, doctor);
        final DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(args);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        initViews(view);
        showDoctorInformation();
        return view;
    }

    private void initViews(View view) {
        mDoctorPhotoImageView = view.findViewById(R.id.doctor_photo_image_view);
        mDoctorNameTextView = view.findViewById(R.id.doctor_name);
        mDoctorRatingTextView = view.findViewById(R.id.doctor_rating);
        mEnrollDoctorButton = view.findViewById(R.id.enroll_doctor_button);
        mEnrollDoctorButton.setOnClickListener(v -> {
            Intent intent = NewRecordActivity.newIntent(getActivity(), mDoctor.getId());
            startActivity(intent);
        });
    }

    private void showDoctorInformation() {
        Picasso.get().load(mDoctor.getPhotoUrl()).into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(mDoctor.getName());
        mDoctorRatingTextView.setText(mDoctor.getRating());
    }
}
