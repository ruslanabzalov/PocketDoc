package com.ruslan.pocketdoc.doc.docs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Doc;

import java.util.List;

public class DocsFragment extends Fragment implements DocsView {

    private static final String ARG_SPEC_ID = "spec_id";
    private static final String ARG_STATION_ID = "station_id";

    private DocsPresenter mDocsPresenter;

    private RecyclerView mDocsRecyclerView;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPEC_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DocsFragment fragment = new DocsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs, container, false);
        mDocsRecyclerView = view.findViewById(R.id.docs_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDocsRecyclerView.setLayoutManager(linearLayoutManager);
        DocsInteractor docsInteractor = new DocsInteractorImpl();
        mDocsPresenter =
                new DocsPresenterImpl(this, docsInteractor,
                        getArguments().getString(ARG_SPEC_ID, null),
                        getArguments().getString(ARG_STATION_ID, null));
        mDocsPresenter.getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDocsPresenter.onDestroy();
    }

    @Override
    public void showDocs(List<Doc> docs) {
        DocsAdapter docsAdapter = new DocsAdapter(docs, this::setFragmentResult);
        mDocsRecyclerView.setAdapter(docsAdapter);
    }

    @Override
    public void showLoadErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Ошибка получения данных с сервера: " + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void setFragmentResult(Doc doc) {
        // TODO: Start new Activity with doc information.
    }
}
