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

public class DocsMetrosFragment extends Fragment {

    // Константы для отправки данных родительской активности.
    private static final String EXTRA_DOCS_METRO_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_id";
    private static final String EXTRA_DOCS_METRO_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_name";

    private RecyclerView mDocsMetrosRecyclerView;

    // Структура данных для хранения наименований станций метро и их идентификаторов.
    private Map<String, String> mDocsMetros = new HashMap<>();
    // Структура данных для хранения списка наименований станций метро.
    private List<String> mMetrosList = new ArrayList<>();

    private String mDocsMetroId;
    private String mDocsMetroName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Запуск фоновых потоков.
        new FetchDocsMetrosTask().execute();
        new FetchDocsMetrosListTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_metros, container, false);
        mDocsMetrosRecyclerView = view.findViewById(R.id.docs_metros_recycler_view);
        mDocsMetrosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    /**
     * Метод, связывающий объект типа DocsMetrosAdapter с объектом типа RecyclerView.
     */
    private void setupAdapter() {
        if (isAdded()) {
            mDocsMetrosRecyclerView.setAdapter(new DocsMetrosAdapter(mMetrosList));
        }
    }

    /**
     * Метод, упаковывающий данные для родительской активности, отправляющий ей результат
     * и завершающий текущущю активность.
     * @param docsMetroId идентификатор станций метро.
     * @param docsMetroName наименование станций метро.
     */
    private void docsMetroResult(String docsMetroId, String docsMetroName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_METRO_ID, docsMetroId);
        data.putExtra(EXTRA_DOCS_METRO_NAME, docsMetroName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    /**
     * Класс для получения данных о всех станциях метро и их идентификаторах в фоновом потоке.
     */
    private class FetchDocsMetrosTask extends AsyncTask<Void, Void, Map<String, String>> {
        /**
         * Метод, обрабатывающий и возвращающй необходимые данные в фоновом потоке.
         * @param params
         * @return объект, хранящий наименования станций метро и их идентификаторы.
         */
        @Override
        protected Map<String, String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsMetrosAndIds();
        }

        /**
         * Метод, выполняющийся в главном потоке после метода doInBackground()
         * и служащий для обновления данных.
         * @param docsMetros объект, хранящий наименования станций метро и их идентификаторы.
         */
        @Override
        protected void onPostExecute(Map<String, String> docsMetros) {
            mDocsMetros = docsMetros;
        }
    }

    /**
     * Класс для получения данных в виде списка всех станций метро в фоновом потоке.
     */
    private class FetchDocsMetrosListTask extends AsyncTask<Void, Void, List<String>> {
        /**
         * Метод, обрабатывающий и возвращающий необходимые данные в фоновом потоке.
         * @param params
         * @return список всех полученных наименований станций метро.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsMetros();
        }

        /**
         * Метод, выполняющийся в главном потоке после метода doInBackground()
         * и служащий для обновления UI.
         * @param metrosList список всех полученных наименований станций метро.
         */
        @Override
        protected void onPostExecute(List<String> metrosList) {
            mMetrosList = metrosList;
            setupAdapter();
        }
    }

    /**
     * Класс, описывающий холдер типа DocsMetrosHolder.
     */
    private class DocsMetrosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocsMetroTextView;

        private String mDocsMetro;

        /**
         * Конструктор для создания холдера типа DocsMetrosHolder и его представления.
         * @param inflater
         * @param parent
         */
        private DocsMetrosHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_docs_metros, parent, false));
            itemView.setOnClickListener(this);
            mDocsMetroTextView = itemView.findViewById(R.id.docs_metro);
        }

        /**
         * Метод, связывающий конкретный холдер типа DocsMetrosHolder
         * с определёнными данными модели.
         * @param docsMetro наименование станции метро.
         */
        public void bind(String docsMetro) {
            mDocsMetro = docsMetro;
            mDocsMetroTextView.setText(mDocsMetro);
        }

        /**
         * Возврат данных дочерней активности при выборе станции метро.
         * @param v
         */
        @Override
        public void onClick(View v) {
            String docsMetroId = mDocsMetros.get(mDocsMetro);
            String docsMetroName = mDocsMetro;
            docsMetroResult(docsMetroId, docsMetroName);
        }
    }

    /**
     * Класс, описывающий адаптер типа DocsMetrosAdapter.
     */
    private class DocsMetrosAdapter
            extends RecyclerView.Adapter<DocsMetrosFragment.DocsMetrosHolder> {

        private List<String> mMetrosList;

        /**
         * Констуктор для создания адаптера типа DocsMetrosAdapter с данными модели.
         * @param docsMetros список наименований станций метро.
         */
        public DocsMetrosAdapter(List<String> docsMetros) {
            mMetrosList = docsMetros;
        }

        /**
         * Метод, создающий адаптер типа DocsMetrosHolder с его представлением.
         * @param viewGroup
         * @param viewType
         * @return
         */
        @Override
        public DocsMetrosFragment.DocsMetrosHolder onCreateViewHolder(ViewGroup viewGroup,
                                                                    int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsMetrosFragment.DocsMetrosHolder(layoutInflater, viewGroup);
        }

        /**
         * Метод, связывающий данные модели для определённой позиции в списке станций метро
         * с их представлением.
         * @param docsMetrosHolder холдер.
         * @param position позиция объекта в списке.
         */
        @Override
        public void onBindViewHolder(DocsMetrosFragment.DocsMetrosHolder docsMetrosHolder,
                                     int position) {
            String docsMetro = mMetrosList.get(position);
            docsMetrosHolder.bind(docsMetro);
        }

        /**
         * Метод, возвращающий общее количество объектов в списке.
         * @return общее количество объекто в списке.
         */
        @Override
        public int getItemCount() {
            return mMetrosList.size();
        }
    }

}
