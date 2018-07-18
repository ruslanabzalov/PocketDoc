package com.ruslan.pocketdoc.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.dialogs.CreateRecordDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class DoctorFragment extends Fragment implements DoctorContract.View {

    private static final String ARG_DOCTOR = "doctor";
    private static final String ARG_DATE = "date";

    private DoctorContract.Presenter mPresenter;

    @Inject
    Picasso mPicasso;

    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorSpecialityTextView;
    private TextView mDoctorExperienceTextView;
    private TextView mDoctorPriceTextView;
    private TextView mDoctorDescriptionTextView;
    private Button mCreateRecordButton;

    private Doctor mDoctor;
    private Date mDate;
    private boolean isDisplayed = false;

    public static Fragment newInstance(Doctor doctor, Date date) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_DOCTOR, doctor);
        arguments.putSerializable(ARG_DATE, date);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(arguments);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        mDoctor = (Doctor) Objects.requireNonNull(getArguments()).getSerializable(ARG_DOCTOR);
        mDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);
        mPresenter = new DoctorPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.doctor_title);
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        mDoctorPhotoImageView = rootView.findViewById(R.id.photo_image_view);
        mDoctorNameTextView = rootView.findViewById(R.id.name_text_view);
        mDoctorSpecialityTextView = rootView.findViewById(R.id.speciality_text_view);
        mDoctorExperienceTextView = rootView.findViewById(R.id.experience_text_view);
        mDoctorPriceTextView = rootView.findViewById(R.id.price_text_view);
        mDoctorDescriptionTextView = rootView.findViewById(R.id.desc_text_view);
        mCreateRecordButton = rootView.findViewById(R.id.create_record);
        mCreateRecordButton.setOnClickListener((View view) -> mPresenter.onCreateRecordButtonClick());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isDisplayed) {
            mPresenter.displayDoctor(mDoctor);
            isDisplayed = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showDoctorInfo(Doctor doctor) {
        mPicasso.load(doctor.getPhotoUrl().replace("_small", ""))
                .into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(doctor.getName());
        mDoctorSpecialityTextView.setText(mDoctor.getSpecialities().get(0).getName());
        mDoctorExperienceTextView.setText(mDoctor.getExperience() + "");
        mDoctorPriceTextView.setText(mDoctor.getPrice() + "");
        mDoctorDescriptionTextView.setText(mDoctor.getDescription());
    }

    @Override
    public void showNewRecordUi() {
        DialogFragment createRecordDialogFragment = new CreateRecordDialogFragment();
        createRecordDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void showErrorMessage(Throwable throwable) {}

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}
}
