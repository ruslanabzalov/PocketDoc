package abzalov.ruslan.pocketdoc.stations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.data.stations.Station;
import abzalov.ruslan.pocketdoc.dialogs.DatePickerDialogFragment;
import abzalov.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.List;
import java.util.Objects;

public class StationsFragment extends Fragment implements StationsContract.View {

    private static final String TAG = "StationsFragment";
    private static final String TAG_LOADING_ERROR_DIALOG_FRAGMENT = "LoadingErrorDialogFragment";

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 2;

    private StationsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StationsAdapter mAdapter;
    private ProgressBar mProgressBar;

    private String mSpecialityId;
    private boolean mAreStationsLoaded;

    public static Fragment newInstance(String specialityId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specialityId);
        StationsFragment stationsFragment = new StationsFragment();
        stationsFragment.setArguments(arguments);
        return stationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mSpecialityId = Objects.requireNonNull(getArguments()).getString(ARG_SPECIALITY_ID);
        mPresenter = new StationsPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.stations_title);
        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        initViews(view);
        return view;
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
        mPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.updateStations(true);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (mRecyclerView.getAdapter() == null) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_stations, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mAreStationsLoaded) {
            setOptionsMenuVisible(menu, true);
        } else {
            setOptionsMenuVisible(menu, false);
        }
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
    public void setOptionsMenuVisible(Menu menu, boolean isVisible) {
        menu.findItem(R.id.item_refresh_stations).setVisible(isVisible);
    }

    @Override
    public void showStations(List<Station> stations) {
        mAreStationsLoaded = true;
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        if (mAdapter == null) {
            mAdapter = new StationsAdapter(stations, mPresenter::chooseStation);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                if (stations.size() != mRecyclerView.getAdapter().getItemCount()) {
                    mAdapter.updateDataSet(stations);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Log.e(TAG, Log.getStackTraceString(throwable));
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
    public void showCalendarUi(String stationId) {
        DialogFragment datePickerDialogFragment =
                DatePickerDialogFragment.newInstance(mSpecialityId, stationId);
        datePickerDialogFragment.show(mFragmentManager, null);
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.stations_refresh);
        int[] swipeRefreshColors = {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        };
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColors);
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> mPresenter.updateStations(false)
        );
        mRecyclerView = view.findViewById(R.id.stations_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressBar = view.findViewById(R.id.stations_progress_bar);
    }
}
