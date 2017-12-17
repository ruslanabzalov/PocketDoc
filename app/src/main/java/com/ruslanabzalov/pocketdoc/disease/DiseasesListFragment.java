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

public class DiseasesListFragment extends Fragment {

    private Disease mDisease;

    private RecyclerView mDiseasesRecyclerView;
    private DiseasesAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Указание, что фрагмент должен получать обратные вызовы
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases_list, container, false);
        mDiseasesRecyclerView = view.findViewById(R.id.diseases_recycler_view);
        // LayoutManager управляет позиционированием элементов,
        // а также определяет поведение прокрутки
        mDiseasesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateDiseaseListFragment();
        return view;
    }

    /**
     * Метод, заполняющий меню.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_diseases_list, menu);
    }

    /**
     * Метод, обрабатывающий нажатие на кнопку "+" меню.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_disease:
                Disease disease = new Disease();
                DiseasesList.get(getActivity()).addDisease(disease);
                Intent intent = DiseaseActivity.newIntent(getActivity(), disease.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DiseaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Disease mDisease;

        private DiseaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_disease, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.disease_title);
            mDateTextView = itemView.findViewById(R.id.disease_date);
        }

        public void bind(Disease disease) {
            mDisease = disease;
            mTitleTextView.setText(mDisease.getTitle());
            mDateTextView.setText(mDisease.getDate().toString());
        }

        /**
         * Метод, запускающий активность-хост DiseaseActivity с соответствующим фрагментом
         * при нажатии на заболевание из списка RecyclerView.
         * @param view
         */
        @Override
        public void onClick(View view) {
            Intent intent = DiseaseActivity.newIntent(getActivity(), mDisease.getId());
            startActivity(intent);
        }

    }

    private class DiseasesAdapter extends RecyclerView.Adapter<DiseaseHolder> {

        private List<Disease> mDiseases;

        private DiseasesAdapter(List<Disease> diseases) {
            mDiseases = diseases;
        }

        @Override
        public DiseaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DiseaseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DiseaseHolder holder, int position) {
            Disease disease = mDiseases.get(position);
            holder.bind(disease);
        }

        @Override
        public int getItemCount() {
            return mDiseases.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDiseaseListFragment();
    }

    /**
     * Метод, настраивающий пользовательский интерфейс фрагмента DiseaseListFragment.
     */
    public void updateDiseaseListFragment() {
        DiseasesList diseasesList = DiseasesList.get(getActivity());
        List<Disease> diseases = diseasesList.getDiseases();
        if (mAdapter == null) {
            mAdapter = new DiseasesAdapter(diseases);
            mDiseasesRecyclerView.setAdapter(mAdapter);
        } else {
            // Уведомление адаптера RecyclerView о том, что данные могли быть изменены
            mAdapter.notifyDataSetChanged();
        }
    }
}
