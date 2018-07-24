package com.ruslan.pocketdoc.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.Utils;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.dialogs.CreateRecordDialogFragment;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class DoctorFragment extends Fragment implements DoctorContract.View {

    private static final String TAG = "DoctorFragment";

    private static final String ARG_DOCTOR = "doctor";
    private static final String ARG_DATE = "date";

    private static final int CREATE_RECORD_DIALOG_REQUEST_CODE = 555;
    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 777;

    private static final String TAG_CREATE_RECORD_DIALOG = "CreateRecordDialogFragment";
    private static final String TAG_LOADING_ERROR_DIALOG = "LoadingErrorDialogFragment";

    private DoctorContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    @Inject
    Picasso mPicasso;

    private ConstraintLayout mDoctorRootViewGroup;
    private ProgressBar mDoctorProgressBar;
    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorSpecialityTextView;
    private TextView mDoctorExperienceTextView;
    private TextView mDoctorPriceTextView;
    private TextView mDoctorDescriptionTextView;
    private Button mCreateRecordButton;

    private int mDoctorId;
    private Date mPreferredDate;
    private boolean isDoctorInfoDisplayed = false;

    public static Fragment newInstance(int doctorId, Date date) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_DOCTOR, doctorId);
        arguments.putSerializable(ARG_DATE, date);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(arguments);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mDoctorId = Objects.requireNonNull(getArguments()).getInt(ARG_DOCTOR);
        mPreferredDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);
        mPresenter = new DoctorPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.doctor_title);
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isDoctorInfoDisplayed) {
            mPresenter.loadDoctorInfo(mDoctorId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOADING_ERROR_DIALOG_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mPresenter.loadDoctorInfo(mDoctorId);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;
            case CREATE_RECORD_DIALOG_REQUEST_CODE:
                break;
        }
    }

    @Override
    public void showDoctorInfo(Doctor doctor) {
        mPicasso.load(doctor.getPhotoUrl().replace("_small", "")).into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(doctor.getName());
        mDoctorSpecialityTextView.setText(Utils.getCorrectSpecialitiesString(doctor.getSpecialities()));
        mDoctorExperienceTextView.setText(Utils.getCorrectExperienceString(doctor.getExperience()));
        mDoctorPriceTextView.setText(Utils.getCorrectPriceString(doctor.getPrice()));
        mDoctorDescriptionTextView.setText(doctor.getDescription());
        isDoctorInfoDisplayed = true;
    }

    @Override
    public void showNewRecordUi() {
        if (mFragmentManager.findFragmentByTag(TAG_CREATE_RECORD_DIALOG) == null) {
            DialogFragment createRecordDialogFragment = new CreateRecordDialogFragment();
            createRecordDialogFragment.setTargetFragment(this, CREATE_RECORD_DIALOG_REQUEST_CODE);
            createRecordDialogFragment.show(mFragmentManager, TAG_CREATE_RECORD_DIALOG);
        }
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        changeAllViewsVisibility(false, mDoctorRootViewGroup);
        Log.d(TAG, throwable.getMessage());
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment.setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
            loadingErrorDialogFragment.show(mFragmentManager, TAG_LOADING_ERROR_DIALOG);
        }
    }

    @Override
    public void showProgressBar() {
        changeAllViewsVisibility(false, mDoctorRootViewGroup);
        mDoctorProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        changeAllViewsVisibility(true, mDoctorRootViewGroup);
        mDoctorProgressBar.setVisibility(View.GONE);
    }

    private void changeAllViewsVisibility(boolean enable, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (enable) {
                child.setVisibility(View.VISIBLE);
            } else {
                child.setVisibility(View.GONE);
            }
            if (child instanceof ViewGroup) {
                changeAllViewsVisibility(enable, (ViewGroup) child);
            }
        }
    }

    private void initViews(View rootView) {
        mDoctorRootViewGroup = rootView.findViewById(R.id.doctor_root_view_group);
        mDoctorProgressBar = rootView.findViewById(R.id.doctor_progress_bar);
        mDoctorPhotoImageView = rootView.findViewById(R.id.photo_image_view);
        mDoctorNameTextView = rootView.findViewById(R.id.name_text_view);
        mDoctorSpecialityTextView = rootView.findViewById(R.id.speciality_text_view);
        mDoctorExperienceTextView = rootView.findViewById(R.id.experience_text_view);
        mDoctorPriceTextView = rootView.findViewById(R.id.price_text_view);
        mDoctorDescriptionTextView = rootView.findViewById(R.id.desc_text_view);
        mCreateRecordButton = rootView.findViewById(R.id.create_record);
        mCreateRecordButton.setOnClickListener((View view) -> mPresenter.onCreateRecordButtonClick());
    }
}
