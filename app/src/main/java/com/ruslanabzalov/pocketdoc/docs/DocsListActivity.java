package com.ruslanabzalov.pocketdoc.docs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocsListActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOC_TYPE = "com.ruslanabzalov.pocketdoc.doc.doc_type";

    /**
     * Статический метод, создающий новый интент для запуска активности DocsListActivity.
     * @param packageContext контекст
     * @param docType специальность искомых врачей
     * @return
     */
    public static Intent newIntent(Context packageContext, String docType) {
        Intent intent = new Intent(packageContext, DocsListActivity.class);
        intent.putExtra(EXTRA_DOC_TYPE, docType);
        return intent;
    }

    /**
     * Метод, создающий фрагмент DocsListFragment.
     * @return фрагмент DocsListFragment
     */
    @Override
    protected Fragment createFragment() {
        String docType = getIntent().getStringExtra(EXTRA_DOC_TYPE);
        return DocsListFragment.newInstance(docType);
    }
}
