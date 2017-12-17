package com.ruslanabzalov.pocketdoc.docs;

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

    private RecyclerView mDocsRecyclerView;
    private List<Doc> mDocs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute(); // Запуск фонового потока
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_docs_list, container, false);
        mDocsRecyclerView = v.findViewById(R.id.docs_recycler_view);
        mDocsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mDocsRecyclerView.setAdapter(new DocsAdapter(mDocs));
        }
    }

    /**
     * Класс для получения данных в бэкграунде.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, List<Doc>> {
        @Override
        protected List<Doc> doInBackground(Void... params) {
            return new DocsFetch().fetchItems();
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

    private class DocsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;

        private Doc mDoc;

        private DocsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_doc, parent, false));
            mNameTextView = itemView.findViewById(R.id.doc_name);
        }

        public void bind(Doc doc) {
            mDoc = doc;
            mNameTextView.setText(mDoc.getName());
        }

        @Override
        public void onClick(View view) {
            // TODO: Открыть подробную ифномацию о враче для оформления заявки.
        }
    }

    private class DocsAdapter extends RecyclerView.Adapter<DocsHolder> {
        private List<Doc> mDocs;

        public DocsAdapter(List<Doc> docs) {
            mDocs = docs;
        }

        @Override
        public DocsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(DocsHolder docsHolder, int position) {
            Doc doc = mDocs.get(position);
            docsHolder.bind(doc);
        }

        @Override
        public int getItemCount() {
            return mDocs.size();
        }
    }
}
