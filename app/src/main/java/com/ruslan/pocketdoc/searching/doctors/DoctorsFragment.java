package com.ruslan.pocketdoc.searching.doctors;

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
import com.ruslan.pocketdoc.searching.BasePresenter;
import com.ruslan.pocketdoc.searching.BaseView;

import java.util.List;

public class DoctorsFragment extends Fragment implements BaseView<Doctor> {

    private static final String ARG_SPEC_ID = "spec_id";
    private static final String ARG_STATION_ID = "station_id";

    private BasePresenter mDoctorsPresenter;

    private RecyclerView mDoctorsRecyclerView;

    private String mSpecialityId;
    private String mStationId;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPEC_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DoctorsFragment fragment = new DoctorsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpecialityId = getArguments().getString(ARG_SPEC_ID, null);
        mStationId = getArguments().getString(ARG_STATION_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        mDoctorsRecyclerView = view.findViewById(R.id.doctors_recycler_view);

        // TODO: Replace with Dagger.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDoctorsRecyclerView.setLayoutManager(linearLayoutManager);
        DoctorsInteractor doctorsInteractor = new DoctorsInteractorImpl();
        mDoctorsPresenter =
                new DoctorsPresenter(this, doctorsInteractor, mSpecialityId, mStationId);
        mDoctorsPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDoctorsPresenter.onDestroy();
    }

    @Override
    public void showList(List<Doctor> doctors) {
        DoctorsAdapter doctorsAdapter = new DoctorsAdapter(doctors, this::setFragmentResult);
        mDoctorsRecyclerView.setAdapter(doctorsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Ошибка получения данных с сервера: " + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void setFragmentResult(Doctor doctor) {
        // TODO: Start new Activity with doctor information.
    }
}
