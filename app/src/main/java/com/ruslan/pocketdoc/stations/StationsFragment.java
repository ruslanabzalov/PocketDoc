package com.ruslan.pocketdoc.stations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ruslan.pocketdoc.dialogs.DatePickerDialogFragment;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 888;

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
        getActivity().setTitle(R.string.stations_title);
        View rootView = inflater.inflate(R.layout.fragment_stations, container, false);
        initViews(rootView);
        mPresenter = new StationsPresenter();
        mPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() == null) {
            mPresenter.loadStations();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.updateStations(true);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (mAdapter == null) {
                getActivity().onBackPressed();
            }
        }
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
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.updateDataSet(stations);
            }
        }
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
        loadingErrorDialogFragment.setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
        loadingErrorDialogFragment.show(getActivity().getSupportFragmentManager(), null);
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
        DialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mSpecialityId, stationId);
        datePickerDialogFragment.setTargetFragment(this, 999);
        datePickerDialogFragment.show(getActivity().getSupportFragmentManager(), null);

//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        Fragment doctorsFragment = DoctorsFragment.newInstance(mSpecialityId, stationId);
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_activity_fragment_container, doctorsFragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .addToBackStack(null)
//                .commit();
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.stations_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.updateStations(false));
        mRecyclerView = view.findViewById(R.id.stations_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        mProgressBar = view.findViewById(R.id.stations_progress_bar);
    }
}
