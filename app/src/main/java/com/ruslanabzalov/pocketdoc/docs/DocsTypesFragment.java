package com.ruslanabzalov.pocketdoc.docs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.DataFetch;
import com.ruslanabzalov.pocketdoc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Фаргмент для отображения списка специализаций врачей.
 */
public class DocsTypesFragment extends Fragment {

    // Константы для отправки данных родительской активности.
    private static final String EXTRA_DOCS_TYPE_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_id";
    private static final String EXTRA_DOCS_TYPE_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_name";

    // Структура данных для хранения наименований специализаций и их идентификаторов.
    private Map<String, String> mDocsTypes = new HashMap<>();
    // Структура данных для хранения списка наименований специализаций.
    private List<String> mTypesList = new ArrayList<>();

    private RecyclerView mDocsTypesRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Запуск фоновых потоков.
        new FetchDocsTypesTask().execute();
        new FetchDocsTypesListTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_types, container, false);
        mDocsTypesRecyclerView = view.findViewById(R.id.docs_types_recycler_view);
        mDocsTypesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    /**
     * Метод, связывающий объект типа DocsTypesAdapter с объектом типа RecyclerView.
     */
    private void setupAdapter() {
        if (isAdded()) {
            mDocsTypesRecyclerView.setAdapter(new DocsTypesAdapter(mTypesList));
        }
    }

    /**
     * Метод, упаковывающий данные для родительской активности, отправляющий ей результат
     * и завершающий текущущю активность.
     * @param docsTypeId идентификатор специализации врачей.
     * @param docsTypeName наименование специализации врачей.
     */
    private void docsTypeResult(String docsTypeId, String docsTypeName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_TYPE_ID, docsTypeId);
        data.putExtra(EXTRA_DOCS_TYPE_NAME, docsTypeName);
        getActivity().setResult(RESULT_OK, data); // Отправка результатов родительской активности.
        getActivity().finish(); // Завершение текущей активности.
    }

    /**
     * Класс для получения данных о всех специализациях и их идентификаторах в фоновом потоке.
     */
    private class FetchDocsTypesTask extends AsyncTask<Void, Void, Map<String, String>> {
        /**
         * Метод, обрабатывающий и возвращающий необходимые данные в фоновом потоке.
         * @param params
         * @return структура данных, хранящая наименования специализаций и их идентификаторы.
         */
        @Override
        protected Map<String, String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsTypesAndIds();
        }

        /**
         * Метод, выполняющийся в главном потоке после метода doInBackground()
         * и служащий для обновления данных.
         * @param docsTypes объект, хранящий идентификатор и соответствующее наименование.
         */
        @Override
        protected void onPostExecute(Map<String, String> docsTypes) {
            mDocsTypes = docsTypes;
        }
    }

    /**
     * Класс для получения данных в виде списка всех специализаций в фоновом потоке.
     */
    private class FetchDocsTypesListTask extends AsyncTask<Void, Void, List<String>> {
        /**
         * Метод, обрабатывающий и возвращающий необходимые данные в фоновом потоке.
         * @param params
         * @return список всех полученных наименований специализаций.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsTypes();
        }

        /**
         * Метод, выполняющийся в главном потоке после метода doInBackground()
         * и служащий для обновления UI.
         * @param typesList список всех полученных наименований специализаций.
         */
        @Override
        protected void onPostExecute(List<String> typesList) {
            mTypesList = typesList;
            setupAdapter();
        }
    }

    /**
     * Класс, описывающий холдер типа DocsTypesHolder.
     */
    private class DocsTypesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocsTypeTextView;

        private String mDocsType;

        /**
         * Конструктор для создания холдера типа DocsTypesHolder и его представления.
         * @param inflater
         * @param parent
         */
        private DocsTypesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_docs_type, parent, false));
            itemView.setOnClickListener(this);
            mDocsTypeTextView = itemView.findViewById(R.id.docs_type);
        }

        /**
         * Метод, связывающий конкретный холдер типа DocsTypesHolder с определёнными данными модели.
         * @param docsType наименование специализации врачей.
         */
        public void bind(String docsType) {
            mDocsType = docsType;
            mDocsTypeTextView.setText(mDocsType);
        }

        /**
         * Возврат данных дочерней активности при выборе специализации.
         * @param v
         */
        @Override
        public void onClick(View v) {
            String docsTypeId = mDocsTypes.get(mDocsType);
            String docsTypeName = mDocsType;
            docsTypeResult(docsTypeId, docsTypeName);
        }
    }

    /**
     * Класс, описывающий адаптер типа DocsTypesAdapter.
     */
    private class DocsTypesAdapter extends RecyclerView.Adapter<DocsTypesFragment.DocsTypesHolder> {

        private List<String> mTypesList;

        /**
         * Констуктор для создания адаптера типа DocsTypesAdapter с данными модели.
         * @param docsTypes список наименований специализаций врачей.
         */
        public DocsTypesAdapter(List<String> docsTypes) {
            mTypesList = docsTypes;
        }

        /**
         * Метод, создающий адаптер типа DocsTypesHolder с его представлением.
         * @param viewGroup
         * @param viewType
         * @return
         */
        @Override
        public DocsTypesFragment.DocsTypesHolder onCreateViewHolder(ViewGroup viewGroup,
                                                                     int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsTypesFragment.DocsTypesHolder(layoutInflater, viewGroup);
        }

        /**
         * Метод, связывающий данные модели для определённой позиции в списке специализаций
         * с их представлением.
         * @param docsTypesHolder холдер.
         * @param position позиция объекта в списке.
         */
        @Override
        public void onBindViewHolder(DocsTypesFragment.DocsTypesHolder docsTypesHolder,
                                     int position) {
            String docsType = mTypesList.get(position);
            docsTypesHolder.bind(docsType);
        }

        /**
         * Метод, возвращающий общее количество объектов в списке.
         * @return общее количество объектов в списке.
         */
        @Override
        public int getItemCount() {
            return mTypesList.size();
        }
    }
}
