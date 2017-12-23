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

import com.ruslanabzalov.pocketdoc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент, отвечающий за отображение списка врачей.
 */
public class DocsListFragment extends Fragment {

    private static final String TAG = "DocsListFragment";

    private static final String ARG_DOC_TYPE = "doc_type";

    private RecyclerView mDocsRecyclerView;

    private List<Doc> mDocs = new ArrayList<>();

    /**
     * Статический метод, предназначенный для создания фрагмента типа DocsListFragment
     * и прикрепления к нему необходимых аргументов.
     * @param docType
     * @return
     */
    public static DocsListFragment newInstance(String docType) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOC_TYPE, docType);
        DocsListFragment fragment = new DocsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute(); // Запуск фонового потока
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_list, container, false);
        mDocsRecyclerView = view.findViewById(R.id.docs_recycler_view);
        mDocsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    /**
     * Метод, связывающий объект типа DocsAdapter с объектом типа RecyclerView.
     */
    private void setupAdapter() {
        if (isAdded()) {
            mDocsRecyclerView.setAdapter(new DocsAdapter(mDocs));
        }
    }

    /**
     * Класс для получения данных в фоновом потоке.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, List<Doc>> {
        @Override
        protected List<Doc> doInBackground(Void... params) {
            return new DocsDataFetch().fetchDocs();
        }

        /**
         * Метод, выполняющийся в главном потоке после метода doInBackground()
         * и служащий для обновления UI.
         * @param docs
         */
        @Override
        protected void onPostExecute(List<Doc> docs) {
            mDocs = docs;
            setupAdapter();
        }
    }

    /**
     * Класс, описывающий определённый объект типа DocsHolder.
     */
    private class DocsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mExperienceTextView;
        private TextView mPriceTextView;
        private TextView mRatingTextView;

        private Doc mDoc;

        /**
         * Конструктор для созданя объекта типа DocsHolder и его представления.
         * @param inflater
         * @param parent
         */
        private DocsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_doc, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.doc_name);
            mExperienceTextView = itemView.findViewById(R.id.doc_experience);
            mPriceTextView = itemView.findViewById(R.id.doc_price);
            mRatingTextView = itemView.findViewById(R.id.doc_rating);
        }

        /**
         * Метод, связывающий конкретный объект типа DocsHolder с определёнными данными модели.
         * @param doc
         */
        public void bind(Doc doc) {
            mDoc = doc;
            mNameTextView.setText(mDoc.getName());
            mExperienceTextView.setText(String.format("Опыт: %s", mDoc.getExperience()));
            mPriceTextView.setText(String.format("Цена: %s", mDoc.getPrice()));
            mRatingTextView.setText(String.format("Рейтинг: %s", mDoc.getRating()));
        }

        /**
         * Метод, запускающий активность DocActivity с дополнениями.
         * @param v
         */
        @Override
        public void onClick(View v) {
            Intent intent = DocActivity.newIntent(getActivity(), mDoc);
            startActivity(intent);
        }
    }

    /**
     * Класс, описывающий объект типа DocsAdapter.
     */
    private class DocsAdapter extends RecyclerView.Adapter<DocsHolder> {
        private List<Doc> mDocs;

        /**
         * Констуктор для создания объекта типа DocsAdapter с данными модели.
         * @param docs
         */
        public DocsAdapter(List<Doc> docs) {
            mDocs = docs;
        }

        /**
         * Метод, создающий объект типа DocsHolder с представлением.
         * @param viewGroup
         * @param viewType
         * @return настроенный объект типа DocsHolder
         */
        @Override
        public DocsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsHolder(layoutInflater, viewGroup);
        }

        /**
         * Метод, связывающий данные модели для определённой позиции в списке с их представлением.
         * @param docsHolder
         * @param position
         */
        @Override
        public void onBindViewHolder(DocsHolder docsHolder, int position) {
            Doc doc = mDocs.get(position);
            docsHolder.bind(doc);
        }

        /**
         * Метод, возвращающий общее количество объектов в списке.
         * @return
         */
        @Override
        public int getItemCount() {
            return mDocs.size();
        }
    }
}
