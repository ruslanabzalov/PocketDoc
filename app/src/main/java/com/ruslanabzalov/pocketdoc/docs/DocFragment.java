package com.ruslanabzalov.pocketdoc.docs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

/**
 * Класс, отвечающий за отображение информации о конкретном враче и записи к нему на приём.
 */
public class DocFragment extends Fragment {

    private static final String ARG_DOC = "doc";

    private Doc mDoc;

    /**
     * Статический метод для создания фрагмента типа DocFragment
     * и прикрепления к нему аргументов.
     * @return
     */
    public static DocFragment newInstance(Doc doc) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOC, doc);
        DocFragment fragment = new DocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoc = (Doc) getArguments().getSerializable(ARG_DOC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc, container, false);
        return view;
    }
}
