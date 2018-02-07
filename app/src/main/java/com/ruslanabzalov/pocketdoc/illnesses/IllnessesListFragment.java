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

public class IllnessesListFragment extends Fragment {

    private Illness mIllness;

    private RecyclerView mDiseasesRecyclerView;
    private DiseasesAdapter mDiseasesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.illnesses_list_fragment_label));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases_list, container, false);
        mDiseasesRecyclerView = view.findViewById(R.id.diseases_recycler_view);
        mDiseasesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateDiseaseListFragment();
        return view;
    }

    /**
     * Метод, отображющий верхнее меню.
     * @param menu объект меню
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_diseases_list, menu);
    }

    /**
     * Метод, обрабатывающий нажатие на кнопку "+" в меню.
     * @param item элемент меню
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_illness:
                Illness illness = new Illness();
                IllnessesList.get(getActivity()).addDisease(illness);
                Intent intent = IllnessActivity.newIntent(getActivity(), illness.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDiseaseListFragment();
    }

    private class DiseaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;

        private Illness mIllness;

        private DiseaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_disease, parent, false));
            itemView.setOnClickListener(this); //
            mTitleTextView = itemView.findViewById(R.id.disease_title);
        }

        void bind(Illness illness) {
            mIllness = illness;
            mTitleTextView.setText(mIllness.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = IllnessActivity.newIntent(getActivity(), mIllness.getId());
            startActivity(intent);
        }

    }

    private class DiseasesAdapter extends RecyclerView.Adapter<DiseaseHolder> {

        private List<Illness> mIllnesses;

        private DiseasesAdapter(List<Illness> illnesses) {
            mIllnesses = illnesses;
        }

        @Override
        public DiseaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DiseaseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DiseaseHolder holder, int position) {
            Illness illness = mIllnesses.get(position);
            holder.bind(illness);
        }

        @Override
        public int getItemCount() {
            return mIllnesses.size();
        }
    }

    private void updateDiseaseListFragment() {
        IllnessesList illnessesList = IllnessesList.get(getActivity());
        List<Illness> illnesses = illnessesList.getIllnesses();
        if (mDiseasesAdapter == null) {
            mDiseasesAdapter = new DiseasesAdapter(illnesses);
            mDiseasesRecyclerView.setAdapter(mDiseasesAdapter);
        } else {
            mDiseasesAdapter.notifyDataSetChanged();
        }
    }
}
