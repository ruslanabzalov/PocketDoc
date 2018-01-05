package com.ruslanabzalov.pocketdoc.docs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocsListActivity extends SingleFragmentActivity {

    // Константы для получения данных от родительской активности.
    private static final String EXTRA_DOCS_TYPE = "com.ruslanabzalov.pocketdoc.doc.docs_type";
    private static final String EXTRA_DOCS_METRO = "com.ruslanabzalov.pocketdoc.doc.docs_metro";
//    private static final String EXTRA_DOCS_DATE = "com.ruslanabzalov.pocketdoc.doc.docs_date";

    /**
     * Метод, создающий интент для запуска активности DocsListActivity.
     * @param packageContext контекст.
     * @param docsType идентификатор специализации врачей.
     * @param docsMetro идентификатор станции метро.
     * @return интент для запуска активности DocsListActivity.
     */
    public static Intent newIntent(Context packageContext, String docsType, String docsMetro) {
//    ,String docsDate) {
        Intent intent = new Intent(packageContext, DocsListActivity.class);
        intent.putExtra(EXTRA_DOCS_TYPE, docsType);
        intent.putExtra(EXTRA_DOCS_METRO, docsMetro);
//        intent.putExtra(EXTRA_DOCS_DATE, docsDate);
        return intent;
    }

    /**
     * Метод, создающий фрагмент DocsListFragment.
     * @return фрагмент DocsListFragment
     */
    @Override
    protected Fragment createFragment() {
        String docsType = getIntent().getStringExtra(EXTRA_DOCS_TYPE);
        String docsMetro = getIntent().getStringExtra(EXTRA_DOCS_METRO);
//        String docsDate = getIntent().getStringExtra(EXTRA_DOCS_DATE);
        return DocsListFragment.newInstance(docsType, docsMetro);//, docsDate);
    }
}
