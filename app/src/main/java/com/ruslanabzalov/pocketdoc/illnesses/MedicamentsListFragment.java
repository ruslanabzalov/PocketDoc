package com.ruslanabzalov.pocketdoc.illnesses;

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

public class MedicamentsListFragment extends Fragment {

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
        updateDrugsListFragment();
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
                Medicament medicament = new Medicament();
                MedicamentsList.get(getActivity()).addMedicament(medicament);
                Intent intent = MedicamentActivity.newIntent(getActivity(), medicament.getId());
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

        private Medicament mMedicament;

        private DrugsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_drug, parent, false));
            itemView.setOnClickListener(this); //
            mTitleTextView = itemView.findViewById(R.id.drug_name);
        }

        public void bind(Medicament medicament) {
            mMedicament = medicament;
            mTitleTextView.setText(mMedicament.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MedicamentActivity.newIntent(getActivity(), mMedicament.getId());
            startActivity(intent);
        }

    }

    private class DrugAdapter extends RecyclerView.Adapter<DrugsHolder> {

        private List<Medicament> mMedicaments;

        private DrugAdapter(List<Medicament> medicaments) {
            mMedicaments = medicaments;
        }

        @Override
        public DrugsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DrugsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DrugsHolder holder, int position) {
            Medicament medicament = mMedicaments.get(position);
            holder.bind(medicament);
        }

        @Override
        public int getItemCount() {
            return mMedicaments.size();
        }
    }

    public void updateDrugsListFragment() {
        MedicamentsList medicamentsList = MedicamentsList.get(getActivity());
        List<Medicament> medicaments = medicamentsList.getMedicaments();
        if (mAdapter == null) {
            mAdapter = new DrugAdapter(medicaments);
            mDrugsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
