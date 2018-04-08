package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocsListActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOCS_TYPE = "com.ruslanabzalov.pocketdoc.doc.docs_type";
    private static final String EXTRA_DOCS_METRO = "com.ruslanabzalov.pocketdoc.doc.docs_metro";

    public static Intent newIntent(Context packageContext, String docsType, String docsMetro) {
        Intent intent = new Intent(packageContext, DocsListActivity.class);
        intent.putExtra(EXTRA_DOCS_TYPE, docsType);
        intent.putExtra(EXTRA_DOCS_METRO, docsMetro);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String docsType = getIntent().getStringExtra(EXTRA_DOCS_TYPE);
        String docsMetro = getIntent().getStringExtra(EXTRA_DOCS_METRO);
        return DocsListFragment.newInstance(docsType, docsMetro);
    }
}
