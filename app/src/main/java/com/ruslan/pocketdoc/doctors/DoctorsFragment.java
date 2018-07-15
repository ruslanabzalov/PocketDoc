package com.ruslan.pocketdoc.doctors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.dialogs.NoDoctorsDialogFragment;
import com.ruslan.pocketdoc.doctor.DoctorFragment;

import java.util.List;

public class DoctorsFragment extends Fragment implements DoctorsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";

    private DoctorsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DoctorsAdapter mAdapter;
    private ProgressBar mProgressBar;

    private FragmentManager mFragmentManager;

    private String mSpecialityId;
    private String mStationId;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DoctorsFragment doctorsFragment = new DoctorsFragment();
        doctorsFragment.setArguments(arguments);
        return doctorsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.doctors_title);
        setHasOptionsMenu(true);
        mFragmentManager = getActivity().getSupportFragmentManager();
        mSpecialityId = getArguments().getString(ARG_SPECIALITY_ID, null);
        mStationId = getArguments().getString(ARG_STATION_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!getActivity().getTitle().equals(getString(R.string.doctors_title))) {
            getActivity().setTitle(R.string.doctors_title);
        }
        View rootView = inflater.inflate(R.layout.fragment_doctors, container, false);
        initViews(rootView);
        mPresenter = new DoctorsPresenter();
        mPresenter.attachView(this);
        return rootView;
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
        // Если текущий фрагмент находится в обратном стеке,
        // то mPresenter зануляется при пересоздании активности.
        // При замене другого фрагмента на ClinicsMapFragment (во время чистки обратного стека)
        // mPresenter снова пытается занулиться, из-за чего возникает NPE.
        // По этой причине здесь необходима проверка на null!
        if (mPresenter != null) {
            mPresenter.detachView();
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
        if (mAdapter == null) {
            if (doctors.size() == 0) {
                DialogFragment noDoctorsDialogFragment = new NoDoctorsDialogFragment();
                noDoctorsDialogFragment.show(mFragmentManager, null);
            } else {
                mAdapter = new DoctorsAdapter(doctors, mPresenter::chooseDoctor);
                mRecyclerView.setAdapter(mAdapter);
            }
        } else {
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.updateDataSet(doctors);
            }
        }
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                getString(R.string.load_error_toast) + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
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
    public void showDoctorInfoUi(Doctor doctor) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, DoctorFragment.newInstance(doctor))
                .addToBackStack(null)
                .commit();
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.doctors_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> mPresenter.updateDoctors(mSpecialityId, mStationId, false)
        );
        mRecyclerView = view.findViewById(R.id.doctors_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mProgressBar = view.findViewById(R.id.doctors_progress_bar);
    }
}
