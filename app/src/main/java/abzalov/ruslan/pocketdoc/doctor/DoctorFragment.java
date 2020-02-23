package abzalov.ruslan.pocketdoc.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.StringUtils;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;
import abzalov.ruslan.pocketdoc.dialogs.CreateRecordDialogFragment;
import abzalov.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.Objects;

import javax.inject.Inject;

public class DoctorFragment extends Fragment implements DoctorContract.View {

    private static final String TAG = "DoctorFragment";

    private static final String ARG_DOCTOR_ID = "doctor_id";

    private static final String TAG_CREATE_RECORD_DIALOG = "CreateRecordDialogFragment";
    private static final String TAG_LOADING_ERROR_DIALOG = "LoadingErrorDialogFragment";

    private static final int CREATE_RECORD_DIALOG_REQUEST_CODE = 5;
    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 7;

    private DoctorContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView mNestedScrollView;
    private ProgressBar mDoctorProgressBar;
    private ImageView mDoctorImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorSpecialityTextView;
    private TextView mDoctorExperienceTextView;
    private TextView mDoctorPriceTextView;
    private TextView mDoctorDescriptionTextView;

    @Inject
    Picasso mPicasso;

    private int mDoctorId;
    private boolean isDoctorInfoDisplayed;

    public static Fragment newInstance(int doctorId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_DOCTOR_ID, doctorId);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(arguments);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        App.getComponent().inject(this);

        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mDoctorId = Objects.requireNonNull(getArguments()).getInt(ARG_DOCTOR_ID);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_doctor, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_doctor_info:
                mPresenter.updateDoctorInfo(mDoctorId, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setOptionsMenuVisible(Menu menu, boolean isVisible) {}

    @Override
    public void showDoctorInfo(Doctor doctor) {
        mPicasso.load(doctor.getPhotoUrl()).into(mDoctorImageView);
        mDoctorNameTextView.setText(doctor.getName());
        mDoctorSpecialityTextView
                .setText(StringUtils.getCorrectSpecialitiesString(doctor.getSpecialities()));
        mDoctorExperienceTextView
                .setText(StringUtils.getCorrectExperienceString(doctor.getExperience()));
        mDoctorPriceTextView.setText(StringUtils.getCorrectPriceString(doctor.getPrice()));
        mDoctorDescriptionTextView.setText(doctor.getDescription());
        isDoctorInfoDisplayed = true;
    }

    @Override
    public void showNewRecordUi() {
        if (mFragmentManager.findFragmentByTag(TAG_CREATE_RECORD_DIALOG) == null) {
            DialogFragment createRecordDialogFragment = new CreateRecordDialogFragment();
            createRecordDialogFragment
                    .setTargetFragment(this, CREATE_RECORD_DIALOG_REQUEST_CODE);
            createRecordDialogFragment.show(mFragmentManager, TAG_CREATE_RECORD_DIALOG);
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        mNestedScrollView.setVisibility(View.GONE);
        Log.d(TAG, throwable.getMessage());
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment
                    .setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
            loadingErrorDialogFragment.show(mFragmentManager, TAG_LOADING_ERROR_DIALOG);
        }
    }

    @Override
    public void showProgressBar() {
        mNestedScrollView.setVisibility(View.GONE);
        mDoctorProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mNestedScrollView.setVisibility(View.VISIBLE);
        mDoctorProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {
        mNestedScrollView.setVisibility(View.VISIBLE);
        mDoctorProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initViews(@NonNull View rootView) {
        mNestedScrollView = rootView.findViewById(R.id.doctor_root_view_group);
        mSwipeRefreshLayout = rootView.findViewById(R.id.doctor_swipe_refresh);
        int[] swipeRefreshColors = {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        };
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColors);
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> mPresenter.updateDoctorInfo(mDoctorId, false));
        mDoctorProgressBar = rootView.findViewById(R.id.doctor_progress_bar);
        mDoctorImageView = rootView.findViewById(R.id.image_doctor);
        mDoctorNameTextView = rootView.findViewById(R.id.name_text_view);
        mDoctorSpecialityTextView = rootView.findViewById(R.id.speciality_text_view);
        mDoctorExperienceTextView = rootView.findViewById(R.id.experience_text_view);
        mDoctorPriceTextView = rootView.findViewById(R.id.price_text_view);
        mDoctorDescriptionTextView = rootView.findViewById(R.id.desc_text_view);
    }
}
