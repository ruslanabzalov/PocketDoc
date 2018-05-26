package com.ruslan.pocketdoc.searching.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.searching.doctors.doctor.DoctorActivity;

import java.util.List;

public class DoctorsFragment extends Fragment implements DoctorsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mDoctorsRecyclerView;

    private DoctorsContract.Presenter mDoctorsPresenter;
    private DoctorsContract.Interactor mDoctorsInteractor;

    private String mSpecialityId;
    private String mStationId;

    public static Fragment newInstance(String specId, String stationId) {
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        final DoctorsFragment fragment = new DoctorsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpecialityId = getArguments().getString(ARG_SPECIALITY_ID, null);
        mStationId = getArguments().getString(ARG_STATION_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        mDoctorsRecyclerView = view.findViewById(R.id.doctors_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDoctorsRecyclerView.setLayoutManager(linearLayoutManager);
        mDoctorsInteractor = new DoctorsInteractor();
        mDoctorsPresenter =
                new DoctorsPresenter(this, mDoctorsInteractor, mSpecialityId, mStationId);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDoctorsPresenter.getDoctors();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDoctorsPresenter.onDestroy();
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

    private void startDoctorActivity(Doctor doctor) {
        final Intent intent = DoctorActivity.newIntent(getActivity(), doctor);
        startActivity(intent);
    }
}
