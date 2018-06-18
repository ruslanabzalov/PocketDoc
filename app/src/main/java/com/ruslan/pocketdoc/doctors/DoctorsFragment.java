package com.ruslan.pocketdoc.doctors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.doctor.DoctorFragment;

import java.util.List;

public class DoctorsFragment extends Fragment implements DoctorsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";

    private RecyclerView mDoctorsRecyclerView;
    private ProgressBar mDoctorsProgressBar;

    private BaseContract.BasePresenter mDoctorsPresenter;

    private String mSpecialityId;
    private String mStationId;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DoctorsFragment fragment = new DoctorsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSpecialityId = getArguments().getString(ARG_SPECIALITY_ID, null);
            mStationId = getArguments().getString(ARG_STATION_ID, null);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctors, container, false);
        mDoctorsRecyclerView = rootView.findViewById(R.id.doctors_recycler_view);
        mDoctorsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDoctorsRecyclerView.setLayoutManager(linearLayoutManager);
        mDoctorsProgressBar = rootView.findViewById(R.id.doctors_progress_bar);
        mDoctorsPresenter = new DoctorsPresenter(this, mSpecialityId, mStationId);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDoctorsPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDoctorsPresenter.stop();
    }

    @Override
    public void showDoctors(List<Doctor> doctorList) {
        DoctorsAdapter doctorsAdapter = new DoctorsAdapter(doctorList, this::startDoctorActivity);
        mDoctorsRecyclerView.setAdapter(doctorsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                getString(R.string.load_error_toast) + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mDoctorsRecyclerView.setVisibility(View.GONE);
        mDoctorsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mDoctorsRecyclerView.setVisibility(View.VISIBLE);
        mDoctorsProgressBar.setVisibility(View.GONE);
    }

    private void startDoctorActivity(Doctor doctor) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, DoctorFragment.newInstance(doctor))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
