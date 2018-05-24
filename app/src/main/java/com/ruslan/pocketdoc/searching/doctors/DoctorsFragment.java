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
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.Doctor;
import com.ruslan.pocketdoc.data.DoctorList;
import com.ruslan.pocketdoc.searching.doctors.doctor.DoctorActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsFragment extends Fragment {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mDoctorsRecyclerView;

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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDoctors();
    }

    private void loadDoctors() {
        final DocDocApi api = DocDocClient.getClient();
        final Call<DoctorList> doctorsListCall = api.getDoctors(
                0, 500, MOSCOW_ID, mSpecialityId, mStationId, "strict",
                "rating", 0, 0, 1, 14
        );
        doctorsListCall.enqueue(new Callback<DoctorList>() {
            @Override
            public void onResponse(@NonNull Call<DoctorList> call,
                                   @NonNull Response<DoctorList> response) {
                final DoctorList doctorList = response.body();
                if (doctorList != null) {
                    final DoctorsAdapter doctorsAdapter =
                            new DoctorsAdapter(doctorList.getDoctorList(),
                                    DoctorsFragment.this::startDoctorActivity);
                    mDoctorsRecyclerView.setAdapter(doctorsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorList> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),
                        getString(R.string.load_error_toast) + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDoctorActivity(Doctor doctor) {
        final int doctorId = doctor.getId();
        final Intent intent = DoctorActivity.newIntent(getActivity(), doctorId);
        startActivity(intent);
    }
}
