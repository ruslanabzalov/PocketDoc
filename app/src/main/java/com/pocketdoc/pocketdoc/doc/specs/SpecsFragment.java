package com.pocketdoc.pocketdoc.doc.specs;

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

import com.pocketdoc.pocketdoc.R;
import com.pocketdoc.pocketdoc.data.Spec;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SpecsFragment extends Fragment implements SpecsContract.MainView {

    private static final String TAG = "SpecsFragment";
    private static final String EXTRA_DOCS_SPECIALITY_ID = "spec_id";
    private static final String EXTRA_DOCS_SPECIALITY_NAME = "spec_name";

    private RecyclerView mSpecsRecyclerView;

    private SpecsContract.Presenter mSpecsPresenter;

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
        mSpecsPresenter = new SpecsPresenter(this, new Fetcher());
        mSpecsPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpecsPresenter.onDestroy();
    }

    @Override
    public void setDataToRecyclerView(List<Spec> specs) {
        SpecsAdapter specsAdapter = new SpecsAdapter(specs, this::setFragmentResult);
        mSpecsRecyclerView.setAdapter(specsAdapter);
    }

    @Override
    public void onResponseFailureMessage(Throwable throwable) {
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
