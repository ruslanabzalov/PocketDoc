package com.ruslan.pocketdoc.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.records.NewRecordFragment;
import com.squareup.picasso.Picasso;

public class DoctorFragment extends Fragment implements DoctorContract.View {

    private static final String ARG_DOCTOR = "doctor";

    private DoctorContract.Presenter mPresenter;

    private Doctor mDoctor;

    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorRatingTextView;
    private Button mEnrollDoctorButton;

    public static Fragment newInstance(Doctor doctor) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOCTOR, doctor);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(args);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.doctor_title);
        mDoctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!getActivity().getTitle().equals(getString(R.string.doctor_title))) {
            getActivity().setTitle(R.string.doctor_title);
        }
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        initViews(view);
        mPresenter = new DoctorPresenter();
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.displayDoctor(mDoctor);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showDoctorInfo(Doctor doctor) {
        Picasso.get()
                .load(doctor.getPhotoUrl())
                .into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(doctor.getName());
        mDoctorRatingTextView.setText(doctor.getRating());
    }

    @Override
    public void showNewRecordUi() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container,
                        NewRecordFragment.newInstance(mDoctor.getId()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {}

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}

    private void initViews(View view) {
        mDoctorPhotoImageView = view.findViewById(R.id.doctor_photo_image_view);
        mDoctorNameTextView = view.findViewById(R.id.doctor_name);
        mDoctorRatingTextView = view.findViewById(R.id.doctor_rating);
        mEnrollDoctorButton = view.findViewById(R.id.enroll_doctor_button);
        mEnrollDoctorButton.setOnClickListener(v -> showNewRecordUi());
    }
}
