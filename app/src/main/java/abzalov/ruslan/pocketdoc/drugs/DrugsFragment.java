package abzalov.ruslan.pocketdoc.drugs;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.drugs.drug.Drug;
import abzalov.ruslan.pocketdoc.drugs.drug.DrugFragment;
import abzalov.ruslan.pocketdoc.drugs.drug.DrugsList;

public class DrugsFragment extends Fragment {

    private Activity mMainActivity;

    private FragmentManager mFragmentManager;

    private RecyclerView mDrugsRecyclerView;
    private DrugsFragment.DrugAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = Objects.requireNonNull(getActivity(), "Activity is null!");
        mFragmentManager = ((AppCompatActivity) mMainActivity).getSupportFragmentManager();
        //getActivity().setTitle("Лекарства");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicaments_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Список лекарств");
        mDrugsRecyclerView = view.findViewById(R.id.drug_list_recycler_view);
        mDrugsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateDrugsListFragment();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_medicaments_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_drug:
                Drug drug = new Drug();
                DrugsList.get(getActivity()).addMedicament(drug);
                showDrugInfo(drug);
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
            super(inflater.inflate(R.layout.list_item_medicament, parent, false));
            itemView.setOnClickListener(this); //
            mTitleTextView = itemView.findViewById(R.id.drug_name);
        }

        public void bind(Drug drug) {
            mDrug = drug;
            mTitleTextView.setText(mDrug.getName());
        }

        @Override
        public void onClick(View v) {
            showDrugInfo(mDrug);
        }

    }

    private class DrugAdapter extends RecyclerView.Adapter<DrugsFragment.DrugsHolder> {

        private List<Drug> mDrugs;

        private DrugAdapter(List<Drug> drugs) {
            mDrugs = drugs;
        }

        @Override
        public DrugsFragment.DrugsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DrugsFragment.DrugsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DrugsFragment.DrugsHolder holder, int position) {
            Drug drug = mDrugs.get(position);
            holder.bind(drug);
        }

        @Override
        public int getItemCount() {
            return mDrugs.size();
        }
    }

    public void updateDrugsListFragment() {
        DrugsList medicamentsList = DrugsList.get(getActivity());
        List<Drug> drugs = medicamentsList.getMedicaments();
        if (mAdapter == null) {
            mAdapter = new DrugsFragment.DrugAdapter(drugs);
            mDrugsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void showDrugInfo(Drug drug) {
        mFragmentManager.beginTransaction()
                .replace(
                        R.id.activity_main_fragment_container,
                        DrugFragment.newInstance(drug.getId())
                )
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
