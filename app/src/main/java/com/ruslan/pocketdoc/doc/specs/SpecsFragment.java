package com.ruslan.pocketdoc.doc.specs;

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
import com.ruslan.pocketdoc.data.Spec;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SpecsFragment extends Fragment implements SpecsContract.MvpView {

    private static final String EXTRA_DOCS_SPECIALITY_ID = "spec_id";
    private static final String EXTRA_DOCS_SPECIALITY_NAME = "spec_name";

    private RecyclerView mSpecsRecyclerView;

    private SpecsContract.MvpPresenter mSpecsMvpPresenter;

    public static String getData(Intent data, String param) {
        return (param.equals("id"))
                ? data.getStringExtra(EXTRA_DOCS_SPECIALITY_ID)
                : data.getStringExtra(EXTRA_DOCS_SPECIALITY_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specs, container, false);
        mSpecsRecyclerView = view.findViewById(R.id.specs_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mSpecsRecyclerView.setLayoutManager(linearLayoutManager);
        mSpecsMvpPresenter = new SpecsMvpPresenter(this, new StationsInteractor());
        mSpecsMvpPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpecsMvpPresenter.onDestroy();
    }

    @Override
    public void showSpecs(List<Spec> specs) {
        SpecsAdapter specsAdapter = new SpecsAdapter(specs, this::setFragmentResult);
        mSpecsRecyclerView.setAdapter(specsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Ошибка получения данных от сервера: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private void setFragmentResult(Spec spec) {
        String specialityId = spec.getId();
        String specialityName = spec.getName();
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_DOCS_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
