package com.ruslan.pocketdoc.stations;

import android.app.Activity;
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
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.dialogs.DatePickerDialogFragment;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.List;
import java.util.Objects;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String TAG = "StationsFragment";
    private static final String TAG_LOADING_ERROR_DIALOG_FRAGMENT = "LoadingErrorDialogFragment";

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 222;

    private StationsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

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
        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mSpecialityId = Objects.requireNonNull(getArguments()).getString(ARG_SPECIALITY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.stations_title);
        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.stations_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.updateStations(false));
        mRecyclerView = view.findViewById(R.id.stations_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressBar = view.findViewById(R.id.stations_progress_bar);
        mPresenter = new StationsPresenter();
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() == null) {
            // Выполняется только при создании или пересоздании фрагмента.
            mPresenter.loadStations();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Если текущий фрагмент находится в обратном стеке,то mPresenter зануляется при пересоздании активности.
        // При замене другого фрагмента на ClinicsMapFragment (во время чистки обратного стека)
        // mPresenter снова пытается занулиться, из-за чего возникает NullPointerException.
        // По этой причине здесь необходима проверка на null!
        if (mPresenter != null) {
            mPresenter.detachView();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.updateStations(true);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (mAdapter == null) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        }
    }

    @Override
    public void showStations(List<Station> stations) {
        // Выполняется только при создании или пересоздании фрагмента.
        if (mAdapter == null) {
            mAdapter = new StationsAdapter(stations, mPresenter::chooseStation);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            // Выполняется при возврате из обратного стека.
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                // Выполняется при обновлении списка.
                if (stations.size() != mRecyclerView.getAdapter().getItemCount()) {
                    mAdapter.updateDataSet(stations);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
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
    public void showDoctorsListUi(String stationId) {
        DialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mSpecialityId, stationId);
        datePickerDialogFragment.show(mFragmentManager, null);
    }
}
