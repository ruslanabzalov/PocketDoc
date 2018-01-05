package com.ruslanabzalov.pocketdoc.disease;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

import java.util.List;

public class DrugsListFragment extends Fragment {

    private RecyclerView mDrugsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugs_list, container, false);
        mDrugsRecyclerView = view.findViewById(R.id.drug_list_recycler_view);
        mDrugsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_drugs_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_drug:
                Drug drug = new Drug();
                DrugsList.get(getActivity()).addDrug(drug);
                Intent intent = DrugActivity.newIntent(getActivity(), drug.getDrugId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDrugsListFragment();
    }

    public void updateDrugsListFragment() {
        DrugsList drugsList = DrugsList.get(getActivity());
        List<Drug> drugs = drugsList.getDrugs();
//        if (mAdapter == null) {
//            mAdapter = new DrugsAdapter(drugs);
//            mDrugsRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.notifyDataSetChanged();
//        }
    }
}
