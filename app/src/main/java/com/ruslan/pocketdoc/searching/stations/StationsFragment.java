package com.ruslan.pocketdoc.searching.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.searching.doctors.DoctorsActivity;

import java.util.List;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private StationsContract.Presenter mStationsPresenter;

    private RecyclerView mStationsRecyclerView;
    private ProgressBar mStationsProgressBar;

    public static Fragment newInstance(String specialityId) {
        Bundle args = new Bundle();
        args.putString(ARG_SPECIALITY_ID, specialityId);
        StationsFragment fragment = new StationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stations, container, false);
        initializeViews(rootView);
        StationsContract.Interactor interactor = new StationsInteractor();
        mStationsPresenter = new StationsPresenter(this, interactor);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mStationsPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStationsPresenter.stop();
    }

    @Override
    public void showStationList(List<Station> stationList) {
        StationsAdapter stationsAdapter =
                new StationsAdapter(stationList, mStationsPresenter::onStationClick);
        mStationsRecyclerView.setAdapter(stationsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                getString(R.string.load_error_toast) + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mStationsRecyclerView.setVisibility(View.GONE);
        mStationsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mStationsRecyclerView.setVisibility(View.VISIBLE);
        mStationsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToDoctorsActivity(String stationId) {
        Intent intent = DoctorsActivity.newIntent(getActivity(), getArguments().getString(ARG_SPECIALITY_ID), stationId);
        startActivity(intent);
    }

    private void initializeViews(View view) {
        mStationsRecyclerView = view.findViewById(R.id.stations_recycler_view);
        mStationsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStationsRecyclerView.setHasFixedSize(true);
        mStationsProgressBar = view.findViewById(R.id.stations_progress_bar);
    }
}
