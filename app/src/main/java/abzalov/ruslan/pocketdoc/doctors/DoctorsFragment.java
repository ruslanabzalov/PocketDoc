package abzalov.ruslan.pocketdoc.doctors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;

import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;
import abzalov.ruslan.pocketdoc.dialogs.CreateRecordDialogFragment;
import abzalov.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;
import abzalov.ruslan.pocketdoc.dialogs.NoDoctorsDialogFragment;
import abzalov.ruslan.pocketdoc.doctor.DoctorFragment;

import java.util.List;
import java.util.Objects;

public class DoctorsFragment extends Fragment implements DoctorsContract.View {

    private static final String TAG = "DoctorsFragment";

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";
    private static final String ARG_DATE = "date";

    private static final String TAG_LOADING_ERROR_DIALOG_FRAGMENT = "LoadingErrorDialogFragment";
    private static final String TAG_CREATE_RECORD_DIALOG_FRAGMENT = "CreateRecordDialogFragment";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 3;
    private static final int NO_DOCTORS_DIALOG_REQUEST_CODE = 4;
    private static final int CREATE_RECORD_DIALOG_REQUEST_CODE = 5;

    private DoctorsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DoctorsAdapter mAdapter;
    private ProgressBar mProgressBar;

    private FragmentManager mFragmentManager;

    private String mSpecialityId;
    private String mStationId;
    private String mPreferredDate;
    private boolean mAreDoctorsLoaded;

    public static Fragment newInstance(String specId, String stationId, String correctDate) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        arguments.putString(ARG_DATE, correctDate);
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
        mPreferredDate = Objects.requireNonNull(getArguments()).getString(ARG_DATE);
        mPresenter = new DoctorsPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.doctors_title);
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() == null) {
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
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_doctors, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NotNull Menu menu) {
        if (mAreDoctorsLoaded) {
            setOptionsMenuVisible(menu, true);
        } else {
            setOptionsMenuVisible(menu, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_refresh_doctors) {
            mPresenter.updateDoctors(mSpecialityId, mStationId, true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setOptionsMenuVisible(Menu menu, boolean isVisible) {
        menu.findItem(R.id.item_refresh_doctors).setVisible(isVisible);
    }

    @Override
    public void showDoctors(List<Doctor> doctors) {
        mAreDoctorsLoaded = true;
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        if (mAdapter == null) {
            if (doctors.size() == 0) {
                DialogFragment noDoctorsDialogFragment = new NoDoctorsDialogFragment();
                noDoctorsDialogFragment
                        .setTargetFragment(this, NO_DOCTORS_DIALOG_REQUEST_CODE);
                noDoctorsDialogFragment.show(mFragmentManager, null);
            } else {
                mPresenter.setDoctorsSchedules(doctors, mPreferredDate);
                mAdapter = new DoctorsAdapter(
                        doctors, mPresenter::chooseDoctor, this::showCreateNewRecordUi
                );
                mRecyclerView.setAdapter(mAdapter);
            }
        } else {
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                if (doctors.size() != mRecyclerView.getAdapter().getItemCount()) {
                    mAdapter.updateDataSet(doctors);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Log.d(TAG, Objects.requireNonNull(throwable.getMessage()));
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG_FRAGMENT) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment
                    .setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
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
                .replace(
                        R.id.activity_main_fragment_container,
                        DoctorFragment.newInstance(doctorId)
                )
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.doctors_refresh);
        int[] swipeRefreshColors = {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        };
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColors);
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> mPresenter.updateDoctors(mSpecialityId, mStationId, false)
        );
        mRecyclerView = view.findViewById(R.id.doctors_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressBar = view.findViewById(R.id.doctors_progress_bar);
    }

    private void showCreateNewRecordUi(int createRecordButtonType, Doctor doctor) {
        if (mFragmentManager.findFragmentByTag(TAG_CREATE_RECORD_DIALOG_FRAGMENT) == null) {
            DialogFragment createRecordDialogFragment;
            if (createRecordButtonType == OnCreateRecordListener.SIMPLE_RECORD_BUTTON) {
                createRecordDialogFragment =
                        CreateRecordDialogFragment
                                .newInstance(OnCreateRecordListener.SIMPLE_RECORD_BUTTON, doctor);
            } else {
                createRecordDialogFragment =
                        CreateRecordDialogFragment
                                .newInstance(OnCreateRecordListener.SCHEDULE_BUTTON, doctor);
            }
            createRecordDialogFragment
                    .setTargetFragment(this, CREATE_RECORD_DIALOG_REQUEST_CODE);
            createRecordDialogFragment.show(mFragmentManager, TAG_CREATE_RECORD_DIALOG_FRAGMENT);
        }
    }
}
