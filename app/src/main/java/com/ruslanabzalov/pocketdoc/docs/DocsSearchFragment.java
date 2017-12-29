package com.ruslanabzalov.pocketdoc.docs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruslanabzalov.pocketdoc.R;

/**
 * Фрагмент, отвечающий за выбор определённой специализации врачей.
 */
public class DocsSearchFragment extends Fragment {

    // Константы для получения данных из дочерних активностей.
    private static final String EXTRA_DOCS_TYPE_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_id";
    private static final String EXTRA_DOCS_TYPE_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_type_name";
    private static final String EXTRA_DOCS_METRO_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_id";
    private static final String EXTRA_DOCS_METRO_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_metro_name";

    // Коды запросов для корректного получения данных от дочерней активности.
    private static final int REQUEST_CODE_TYPES = 0;
    private static final int REQUEST_CODE_METROS = 1;
//    private static final int REQUEST_CODE_DATE = 2;

    private String mDocsTypeId;
    private String mDocsTypeName;
    private String mDocsMetroId;
    private String mDocsMetroName;
//    private String mDocsDate;

    private Button mDocsTypeButton;
    private Button mDocsMetroButton;
//    private Button mDocsDateButton;
    private Button mDocsSearchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_search, container, false);
        mDocsTypeButton = view.findViewById(R.id.docs_types);
        mDocsTypeButton.setOnClickListener((View v) -> {
            Intent intent = DocsSearchParamsActivity
                    .newIntent(getActivity(), "DocsTypesFragment");
            startActivityForResult(intent, REQUEST_CODE_TYPES);
        });
        mDocsMetroButton = view.findViewById(R.id.docs_metros);
        mDocsMetroButton.setOnClickListener((View v) -> {
            Intent intent = DocsSearchParamsActivity
                    .newIntent(getActivity(), "DocsMetrosFragment");
            startActivityForResult(intent, REQUEST_CODE_METROS);
        });
//        mDocsDateButton = view.findViewById(R.id.docs_date);
//        mDocsDateButton.setOnClickListener((View v) -> {
//            Intent intent = DocsListActivity.newIntent(getActivity());
//            startActivityForResult(intent, REQUEST_CODE_DATE);
//        });
        mDocsSearchButton = view.findViewById(R.id.docs_search);
        mDocsSearchButton.setEnabled(false);
        mDocsSearchButton.setOnClickListener((View v) -> {
            Intent intent = DocsListActivity.newIntent(getActivity(), mDocsTypeId, mDocsMetroId);
            startActivity(intent);
        });
        return view;
    }

    /**
     * Метод для получения результатов от дочерних активностей.
     * @param requestCode код запроса.
     * @param resultCode код результата.
     * @param data данные, переданные главной активности.
     */
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
            mDocsTypeButton.setEnabled(false);
            checkDocsTypeAndMetroButtons();
        } else if (requestCode == REQUEST_CODE_METROS) {
            if (data == null) {
                return;
            }
            mDocsMetroId = data.getStringExtra(EXTRA_DOCS_METRO_ID);
            mDocsMetroName = data.getStringExtra(EXTRA_DOCS_METRO_NAME);
            mDocsMetroButton.setText(mDocsMetroName);
            mDocsMetroButton.setEnabled(false);
            checkDocsTypeAndMetroButtons();
        }
    }

    /**
     * Метод, проверяющий выбор всех необходимых параметров для поиска врачей.
     */
    private void checkDocsTypeAndMetroButtons() {
        if (!mDocsTypeButton.isEnabled() && !mDocsMetroButton.isEnabled()) {
            mDocsSearchButton.setEnabled(true);
        }
    }
}
