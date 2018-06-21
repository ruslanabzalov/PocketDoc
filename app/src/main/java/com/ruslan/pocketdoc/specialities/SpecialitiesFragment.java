package com.ruslan.pocketdoc.specialities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.stations.StationsFragment;

import java.util.List;

public class SpecialitiesFragment extends Fragment implements SpecialitiesContract.View {

    private SpecialitiesContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_specialities, container, false);
        initializeViews(rootView);
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
        mPresenter.detach();
    }

    @Override
    public void showSpecialities(List<Speciality> specialityList) {
        SpecialitiesAdapter specialitiesAdapter =
                new SpecialitiesAdapter(specialityList, mPresenter::onSpecialityClick);
        mRecyclerView.setAdapter(specialitiesAdapter);
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
    public void showStationListUi(String specialityId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, StationsFragment.newInstance(specialityId))
                .addToBackStack(null)
                .commit();
    }

    private void initializeViews(View view) {
        mRecyclerView = view.findViewById(R.id.specialities_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mProgressBar = view.findViewById(R.id.specialities_progress_bar);
    }
}
