package com.ruslan.pocketdoc.doctors;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;
import com.ruslan.pocketdoc.dialogs.NoDoctorsDialogFragment;
import com.ruslan.pocketdoc.doctor.DoctorFragment;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DoctorsFragment extends Fragment implements DoctorsContract.View {

    private static final String TAG = "DoctorsFragment";

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";
    private static final String ARG_DATE = "date";

    private static final String TAG_LOADING_ERROR_DIALOG_FRAGMENT = "LoadingErrorDialogFragment";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 333;
    private static final int NO_DOCTORS_DIALOG_REQUEST_CODE = 444;

    private DoctorsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DoctorsAdapter mAdapter;
    private ProgressBar mProgressBar;

    private FragmentManager mFragmentManager;

    private String mSpecialityId;
    private String mStationId;
    private Date mDate;

    public static Fragment newInstance(String specId, String stationId, Date date) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        arguments.putSerializable(ARG_DATE, date);
        DoctorsFragment doctorsFragment = new DoctorsFragment();
        doctorsFragment.setArguments(arguments);
        return doctorsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mSpecialityId = Objects.requireNonNull(getArguments()).getString(ARG_SPECIALITY_ID);
        mStationId = Objects.requireNonNull(getArguments()).getString(ARG_STATION_ID);
        mDate = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);
        mPresenter = new DoctorsPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.doctors_title);
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.doctors_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.updateDoctors(mSpecialityId, mStationId, false));
        mRecyclerView = view.findViewById(R.id.doctors_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressBar = view.findViewById(R.id.doctors_progress_bar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() == null) {
            // Выполняется только при создании или пересоздании фрагмента.
            mPresenter.loadDoctors(mSpecialityId, mStationId);
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
                    mPresenter.updateDoctors(mSpecialityId, mStationId, true);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (mRecyclerView.getAdapter() == null) {
                        Objects.requireNonNull(getActivity()).onBackPressed();
                    }
                }
                break;
            case NO_DOCTORS_DIALOG_REQUEST_CODE:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_doctors, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_doctors:
                mPresenter.updateDoctors(mSpecialityId, mStationId, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showDoctors(List<Doctor> doctors) {
        // Выполняется только при создании или пересоздании фрагмента.
        if (mAdapter == null) {
            if (doctors.size() == 0) {
                DialogFragment noDoctorsDialogFragment = new NoDoctorsDialogFragment();
                noDoctorsDialogFragment.setTargetFragment(this, NO_DOCTORS_DIALOG_REQUEST_CODE);
                noDoctorsDialogFragment.show(mFragmentManager, null);
            } else {
                mAdapter = new DoctorsAdapter(doctors, mPresenter::chooseDoctor);
                mRecyclerView.setAdapter(mAdapter);
            }
        } else {
            // Выполняется при возврате из обратного стека.
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                // Выполняется при обновлении списка.
                if (doctors.size() != mRecyclerView.getAdapter().getItemCount()) {
                    mAdapter.updateDataSet(doctors);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
        // Если LoadingErrorDialogFragment уже отображался перед сменой ориентации устройства,
        // то этот же DialogFragment не пересоздаётся заново, а продолжает отображаться.
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG_FRAGMENT) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment.setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
            loadingErrorDialogFragment.show(mFragmentManager, TAG_LOADING_ERROR_DIALOG_FRAGMENT);
        }
    }

    @Override
    public void showProgressBar() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDoctorInfoUi(int doctorId) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, DoctorFragment.newInstance(doctorId, mDate))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
