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

public class DocsTypesFragment extends Fragment {

    private static final String EXTRA_DOCS_TYPE_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_id";
    private static final String EXTRA_DOCS_TYPE_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_name";

    private Map<String, String> mDocsTypes = new HashMap<>();
    private List<String> mTypesList = new ArrayList<>();

    private RecyclerView mDocsTypesRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Специализации врачей");
        // Запуск фоновых потоков
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

    private void setupAdapter() {
        if (isAdded()) {
            mDocsTypesRecyclerView.setAdapter(new DocsTypesAdapter(mTypesList));
        }
    }

    private void docsTypeResult(String docsTypeId, String docsTypeName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_TYPE_ID, docsTypeId);
        data.putExtra(EXTRA_DOCS_TYPE_NAME, docsTypeName);
        getActivity().setResult(RESULT_OK, data); // Отправка результатов родительской активности
        getActivity().finish(); // Завершение текущей активности
    }

    private class FetchDocsTypesTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsTypesAndIds();
        }

        @Override
        protected void onPostExecute(Map<String, String> docsTypes) {
            mDocsTypes = docsTypes;
        }
    }

    private class FetchDocsTypesListTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsTypes();
        }

        @Override
        protected void onPostExecute(List<String> typesList) {
            mTypesList = typesList;
            setupAdapter();
        }
    }

    private class DocsTypesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocsTypeTextView;

        private String mDocsType;

        private DocsTypesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_docs_type, parent, false));
            itemView.setOnClickListener(this);
            mDocsTypeTextView = itemView.findViewById(R.id.docs_type);
        }

        public void bind(String docsType) {
            mDocsType = docsType;
            mDocsTypeTextView.setText(mDocsType);
        }

        @Override
        public void onClick(View v) {
            String docsTypeId = mDocsTypes.get(mDocsType);
            String docsTypeName = mDocsType;
            docsTypeResult(docsTypeId, docsTypeName);
        }
    }

    private class DocsTypesAdapter extends RecyclerView.Adapter<DocsTypesFragment.DocsTypesHolder> {

        private List<String> mTypesList;

        public DocsTypesAdapter(List<String> docsTypes) {
            mTypesList = docsTypes;
        }

        @Override
        public DocsTypesFragment.DocsTypesHolder onCreateViewHolder(ViewGroup viewGroup,
                                                                     int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsTypesFragment.DocsTypesHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(DocsTypesFragment.DocsTypesHolder docsTypesHolder,
                                     int position) {
            String docsType = mTypesList.get(position);
            docsTypesHolder.bind(docsType);
        }

        @Override
        public int getItemCount() {
            return mTypesList.size();
        }
    }
}
