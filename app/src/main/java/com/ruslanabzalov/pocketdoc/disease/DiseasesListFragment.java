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
        getActivity().setTitle("Заболевания");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diseases_list, container, false);
        mDiseasesRecyclerView = v.findViewById(R.id.diseases_recycler_view);
        // Выбор объекта для позиционирования элементов списка
        mDiseasesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateDiseaseListFragment();
        return v;
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
     * @param item элемент меню
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

    /**
     * Метод жизненного цикла, выполняющийся обновление UI фрагмента DiseaseFragmentList
     * при возврате из другой активности.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateDiseaseListFragment();
    }

    /**
     * Класс, описывающий определённый объект типа DiseaseHolder.
     */
    private class DiseaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;

        private Disease mDisease;

        /**
         * Конструктор для создания объекта типа ViewHolder с его представлением.
         * @param inflater
         * @param parent
         */
        private DiseaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_disease, parent, false));
            itemView.setOnClickListener(this); //
            mTitleTextView = itemView.findViewById(R.id.disease_title);
        }

        /**
         * Метод, связывающий конкретный объект типа DiseaseHolder с определёнными данными модели.
         * @param disease конкретное заболевание
         */
        public void bind(Disease disease) {
            mDisease = disease;
            mTitleTextView.setText(mDisease.getTitle());
        }

        /**
         * Метод, запускающий активность DiseaseActivity с дополнениями.
         * @param v
         */
        @Override
        public void onClick(View v) {
            Intent intent = DiseaseActivity.newIntent(getActivity(), mDisease.getId());
            startActivity(intent);
        }

    }

    /**
     * Класс, описывающий объект типа DiseasesAdapter.
     */
    private class DiseasesAdapter extends RecyclerView.Adapter<DiseaseHolder> {

        private List<Disease> mDiseases;

        /**
         * Констуктор для создания объекта типа DiseasesAdapter с данными модели.
         * @param diseases список заболеваний пользователя
         */
        private DiseasesAdapter(List<Disease> diseases) {
            mDiseases = diseases;
        }

        /**
         * Метод, создающий объект типа DiseaseHolder с представлением.
         * @param parent
         * @param viewType
         * @return настроенный объект типа DiseaseHolder
         */
        @Override
        public DiseaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DiseaseHolder(layoutInflater, parent);
        }

        /**
         * Метод, связывающий данные модели для определённой позиции в списке с их представлением.
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(DiseaseHolder holder, int position) {
            Disease disease = mDiseases.get(position);
            holder.bind(disease);
        }

        /**
         * Метод, возвращающий общее количество объектов в списке.
         * @return количество объектов в списке
         */
        @Override
        public int getItemCount() {
            return mDiseases.size();
        }
    }

    /**
     * Метод, связывающий объект типа DiseasesAdapter с RecyclerView
     * и настраивающий UI фрагмента DiseaseListFragment.
     */
    public void updateDiseaseListFragment() {
        DiseasesList diseasesList = DiseasesList.get(getActivity());
        List<Disease> diseases = diseasesList.getDiseases();
        if (mAdapter == null) {
            mAdapter = new DiseasesAdapter(diseases);
            mDiseasesRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
