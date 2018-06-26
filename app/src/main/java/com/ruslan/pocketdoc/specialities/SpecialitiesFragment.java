package com.ruslan.pocketdoc.specialities;

import android.content.Intent;
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
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.history.RecordsHistoryActivity;
import com.ruslan.pocketdoc.stations.StationsFragment;

import java.util.List;

public class SpecialitiesFragment extends Fragment implements SpecialitiesContract.View {

    private SpecialitiesContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialities, container, false);
        initViews(rootView);
        mPresenter = new SpecialitiesPresenter();
        mPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadSpecialities();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_specialities, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_specialities:
                mPresenter.onMenuItemRefreshClick();
                return true;
            case R.id.item_records_history:
                mPresenter.onMenuItemRecordsHistoryClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSpecialities(List<Speciality> specialities) {
        SpecialitiesAdapter adapter = new SpecialitiesAdapter(specialities, mPresenter::onSpecialityClick);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
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
    public void showStationListUi(String specialityId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, StationsFragment.newInstance(specialityId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showRecordsHistoryListUi() {
        Intent intent = new Intent(getActivity(), RecordsHistoryActivity.class);
        startActivity(intent);
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.specialities_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.updateSpecialities(false));
        mRecyclerView = view.findViewById(R.id.specialities_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mProgressBar = view.findViewById(R.id.specialities_progress_bar);
    }
}
