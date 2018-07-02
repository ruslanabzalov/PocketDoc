package com.ruslan.pocketdoc.stations;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.doctors.DoctorsFragment;

import java.util.List;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private StationsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StationsAdapter mAdapter;
    private ProgressBar mProgressBar;

    private String mSpecialityId;

    public static Fragment newInstance(String specialityId) {
        Bundle args = new Bundle();
        args.putString(ARG_SPECIALITY_ID, specialityId);
        StationsFragment stationsFragment = new StationsFragment();
        stationsFragment.setArguments(args);
        return stationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSpecialityId = getArguments().getString(ARG_SPECIALITY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stations, container, false);
        initViews(rootView);
        mPresenter = new StationsPresenter();
        mPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadStations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_stations, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_stations:
                mPresenter.updateStations(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showStations(List<Station> stations) {
        if (mAdapter == null) {
            mAdapter = new StationsAdapter(stations, mPresenter::chooseStation);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateDataSet(stations);
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
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
    public void showDoctorsListUi(String stationId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment doctorsFragment = DoctorsFragment.newInstance(mSpecialityId, stationId);
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, doctorsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.stations_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.updateStations(false));
        mRecyclerView = view.findViewById(R.id.stations_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressBar = view.findViewById(R.id.stations_progress_bar);
    }
}
