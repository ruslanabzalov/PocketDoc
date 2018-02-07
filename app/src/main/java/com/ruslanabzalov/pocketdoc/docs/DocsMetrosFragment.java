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

    private static final String EXTRA_DOCS_METRO_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_id";
    private static final String EXTRA_DOCS_METRO_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_name";

    private RecyclerView mDocsMetrosRecyclerView;

    private Map<String, String> mDocsMetros = new HashMap<>();
    private List<String> mMetrosList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Список станций метро");
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

    private void setupAdapter() {
        if (isAdded()) {
            mDocsMetrosRecyclerView.setAdapter(new DocsMetrosAdapter(mMetrosList));
        }
    }

    private void docsMetroResult(String docsMetroId, String docsMetroName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_METRO_ID, docsMetroId);
        data.putExtra(EXTRA_DOCS_METRO_NAME, docsMetroName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    private class FetchDocsMetrosTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsMetrosAndIds();
        }

        @Override
        protected void onPostExecute(Map<String, String> docsMetros) {
            mDocsMetros = docsMetros;
        }
    }

    private class FetchDocsMetrosListTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            return new DataFetch().fetchDocsMetros();
        }

        @Override
        protected void onPostExecute(List<String> metrosList) {
            mMetrosList = metrosList;
            setupAdapter();
        }
    }

    private class DocsMetrosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocsMetroTextView;

        private String mDocsMetro;

        private DocsMetrosHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_docs_metros, parent, false));
            itemView.setOnClickListener(this);
            mDocsMetroTextView = itemView.findViewById(R.id.docs_metro);
        }

        public void bind(String docsMetro) {
            mDocsMetro = docsMetro;
            mDocsMetroTextView.setText(mDocsMetro);
        }

        @Override
        public void onClick(View v) {
            String docsMetroId = mDocsMetros.get(mDocsMetro);
            String docsMetroName = mDocsMetro;
            docsMetroResult(docsMetroId, docsMetroName);
        }
    }

    private class DocsMetrosAdapter
            extends RecyclerView.Adapter<DocsMetrosFragment.DocsMetrosHolder> {

        private List<String> mMetrosList;

        public DocsMetrosAdapter(List<String> docsMetros) {
            mMetrosList = docsMetros;
        }

        @Override
        public DocsMetrosFragment.DocsMetrosHolder onCreateViewHolder(ViewGroup viewGroup,
                                                                    int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsMetrosFragment.DocsMetrosHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(DocsMetrosFragment.DocsMetrosHolder docsMetrosHolder,
                                     int position) {
            String docsMetro = mMetrosList.get(position);
            docsMetrosHolder.bind(docsMetro);
        }

        @Override
        public int getItemCount() {
            return mMetrosList.size();
        }
    }

}
