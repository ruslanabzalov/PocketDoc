package com.ruslanabzalov.pocketdoc.doctors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruslanabzalov.pocketdoc.R;

public class DocsSearchFragment extends Fragment {

    private static final String EXTRA_DOCS_TYPE_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_id";
    private static final String EXTRA_DOCS_TYPE_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_name";
    private static final String EXTRA_DOCS_METRO_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_id";
    private static final String EXTRA_DOCS_METRO_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_name";

    private static final int REQUEST_CODE_TYPES = 0;
    private static final int REQUEST_CODE_METROS = 1;

    private String mDocsTypeId;
    private String mDocsTypeName;
    private String mDocsMetroId;
    private String mDocsMetroName;

    private Button mDocsTypeButton;
    private Button mDocsMetroButton;
    private Button mDocsSearchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.docs_search_fragment_label));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_search, container, false);
        mDocsTypeButton = view.findViewById(R.id.docs_types);
        mDocsTypeButton.setOnClickListener((View v) -> {
            Intent intent = DocsSearchParamsActivity
                    .newIntent(getActivity(), "SpecialitiesFragment");
            startActivityForResult(intent, REQUEST_CODE_TYPES);
        });
        mDocsMetroButton = view.findViewById(R.id.docs_metros);
        mDocsMetroButton.setOnClickListener((View v) -> {
            Intent intent = DocsSearchParamsActivity
                    .newIntent(getActivity(), "StationsFragment");
            startActivityForResult(intent, REQUEST_CODE_METROS);
        });
        mDocsSearchButton = view.findViewById(R.id.docs_search);
        mDocsSearchButton.setEnabled(false);
        mDocsSearchButton.setOnClickListener((View v) -> {
            Intent intent = DocsListActivity.newIntent(getActivity(), mDocsTypeId, mDocsMetroId);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_docs_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.visits:
                // Открытие активности, отображающей историю посещений.
                Intent intent = new Intent(getContext(), RecordsHistoryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_TYPES) {
            if (data == null) {
                return;
            }
            mDocsTypeId = data.getStringExtra(EXTRA_DOCS_TYPE_ID);
            mDocsTypeName = data.getStringExtra(EXTRA_DOCS_TYPE_NAME);
            mDocsTypeButton.setText(mDocsTypeName);
            checkButtons();
        }
        if (requestCode == REQUEST_CODE_METROS) {
            if (data == null) {
                return;
            }
            mDocsMetroId = data.getStringExtra(EXTRA_DOCS_METRO_ID);
            mDocsMetroName = data.getStringExtra(EXTRA_DOCS_METRO_NAME);
            mDocsMetroButton.setText(mDocsMetroName);
            checkButtons();
        }
    }

    private void checkButtons() {
        if (!mDocsTypeButton.getText().equals("") && !mDocsMetroButton.getText().equals("")) {
            mDocsSearchButton.setEnabled(true);
        }
    }
}
