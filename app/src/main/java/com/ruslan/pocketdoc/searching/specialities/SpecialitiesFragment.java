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
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.searching.BaseInteractor;
import com.ruslan.pocketdoc.searching.BasePresenter;
import com.ruslan.pocketdoc.searching.BaseView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SpecialitiesFragment extends Fragment implements BaseView<Speciality> {

    private static final String EXTRA_DOCS_SPECIALITY_ID = "spec_id";
    private static final String EXTRA_DOCS_SPECIALITY_NAME = "spec_name";

    private RecyclerView mSpecsRecyclerView;

    private BasePresenter mSpecsMvpPresenter;

    public static String getData(Intent data, String param) {
        return (param.equals("id"))
                ? data.getStringExtra(EXTRA_DOCS_SPECIALITY_ID)
                : data.getStringExtra(EXTRA_DOCS_SPECIALITY_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialities, container, false);
        mSpecsRecyclerView = view.findViewById(R.id.specs_recycler_view);

        // TODO: Replace with Dagger.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mSpecsRecyclerView.setLayoutManager(linearLayoutManager);
        BaseInteractor<Speciality> specInteractor = new SpecialitiesInteractor();
        mSpecsMvpPresenter = new SpecialitiesPresenter(this, specInteractor);
        mSpecsMvpPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpecsMvpPresenter.onDestroy();
    }

    @Override
    public void showList(List<Speciality> specialities) {
        SpecialitiesAdapter specialitiesAdapter = new SpecialitiesAdapter(specialities, this::setFragmentResult);
        mSpecsRecyclerView.setAdapter(specialitiesAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Ошибка получения данных от сервера: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private void setFragmentResult(Speciality speciality) {
        String specialityId = speciality.getId();
        String specialityName = speciality.getName();
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_DOCS_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
