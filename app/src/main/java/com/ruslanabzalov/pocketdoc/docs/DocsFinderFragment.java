package com.ruslanabzalov.pocketdoc.docs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

/**
 * Класс, отвечающий за поиск врача по определённым критериям.
 */
public class DocsFinderFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_docs_finder, container, false);
    }
}
