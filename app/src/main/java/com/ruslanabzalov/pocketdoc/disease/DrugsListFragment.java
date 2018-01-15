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
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;

import java.util.List;

public class DrugsListFragment extends Fragment {

    private RecyclerView mDrugsRecyclerView;
    private DrugAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Лекарства");
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

    private class DrugsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;

        private Drug mDrug;

        private DrugsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_drug, parent, false));
            itemView.setOnClickListener(this); //
            mTitleTextView = itemView.findViewById(R.id.disease_title);
        }

        public void bind(Drug drug) {
            mDrug = drug;
            mTitleTextView.setText(mDrug.getDrugName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = DrugActivity.newIntent(getActivity(), mDrug.getDrugId());
            startActivity(intent);
        }

    }

    private class DrugAdapter extends RecyclerView.Adapter<DrugsHolder> {

        private List<Drug> mDrugs;

        private DrugAdapter(List<Drug> drugs) {
            mDrugs = drugs;
        }

        @Override
        public DrugsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DrugsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DrugsHolder holder, int position) {
            Drug drug = mDrugs.get(position);
            holder.bind(drug);
        }

        @Override
        public int getItemCount() {
            return mDrugs.size();
        }
    }

    public void updateDrugsListFragment() {
        DrugsList drugsList = DrugsList.get(getActivity());
        List<Drug> drugs = drugsList.getDrugs();
        if (mAdapter == null) {
            mAdapter = new DrugAdapter(drugs);
            mDrugsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
