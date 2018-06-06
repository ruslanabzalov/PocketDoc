package com.ruslan.pocketdoc.searching.specialities;

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
import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.searching.BaseContract;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SpecialitiesFragment extends Fragment implements SpecialitiesContract.View {

    private static final String EXTRA_SPECIALITY_ID = "speciality_id";
    private static final String EXTRA_SPECIALITY_NAME = "speciality_name";

    private BaseContract.BasePresenter mSpecialitiesPresenter;
    private SpecialitiesContract.Interactor mSpecialitiesInteractor;

    private RecyclerView mSpecialitiesRecyclerView;
    private ProgressBar mSpecialitiesProgressBar;

    public static String getSpecialitiesFragmentResult(Intent data, String parameter) {
        switch (parameter) {
            case "id":
                return data.getStringExtra(EXTRA_SPECIALITY_ID);
            case "name":
                return data.getStringExtra(EXTRA_SPECIALITY_NAME);
            default:
                return null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialities, container, false);
        mSpecialitiesRecyclerView = rootView.findViewById(R.id.specs_recycler_view);
        mSpecialitiesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mSpecialitiesRecyclerView.setLayoutManager(linearLayoutManager);
        mSpecialitiesProgressBar = rootView.findViewById(R.id.specialities_progress_bar);
        mSpecialitiesInteractor = new SpecialitiesInteractor();
        mSpecialitiesPresenter = new SpecialitiesPresenter(this, mSpecialitiesInteractor);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSpecialitiesPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpecialitiesPresenter.onDestroy();
    }

    @Override
    public void showSpecialities(List<Speciality> specialityList) {
        SpecialitiesAdapter specialitiesAdapter =
                new SpecialitiesAdapter(specialityList, this::setSpecialitiesFragmentResult);
        mSpecialitiesRecyclerView.setAdapter(specialitiesAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                getString(R.string.load_error_toast) + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mSpecialitiesRecyclerView.setVisibility(View.GONE);
        mSpecialitiesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mSpecialitiesRecyclerView.setVisibility(View.VISIBLE);
        mSpecialitiesProgressBar.setVisibility(View.GONE);
    }

    private void setSpecialitiesFragmentResult(Speciality speciality) {
        String specialityId = speciality.getId();
        String specialityName = speciality.getName();
        Intent data = new Intent();
        data.putExtra(EXTRA_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
