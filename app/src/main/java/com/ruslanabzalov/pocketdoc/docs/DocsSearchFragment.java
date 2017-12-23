package com.ruslanabzalov.pocketdoc.docs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ruslanabzalov.pocketdoc.R;

/**
 * Фрагмент, отвечающий за выбор критериев для поиска врача.
 */
public class DocsSearchFragment extends Fragment {

    private String mDocType;

    private EditText mDocTypeEditText;
    private Button mSearchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_search, container, false);
        mDocTypeEditText = view.findViewById(R.id.doc_edit_text);
        mDocTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDocType = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mSearchButton = view.findViewById(R.id.docs_search_button);
        mSearchButton.setOnClickListener((View v) -> {
            Intent intent = DocsListActivity.newIntent(getActivity(), mDocType);
            startActivity(intent);
        });
        return view;
    }
}
