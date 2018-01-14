package com.ruslanabzalov.pocketdoc.docs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocRecordActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOC = "com.ruslanabzalov.pocketdoc.docs.doc";

    /**
     * Метод, предназначенный для создания интента.
     * @param packageContext
     * @param doc
     * @return
     */
    public static Intent newIntent(Context packageContext, Doc doc) {
        Intent intent = new Intent(packageContext, DocRecordActivity.class);
        intent.putExtra(EXTRA_DOC, doc);
        return intent;
    }

    /**
     * Метод, предназначенный для создания фрагмента DocRecordFragment.
     * @return
     */
    @Override
    protected Fragment createFragment() {
        Doc doc = (Doc) getIntent().getSerializableExtra(EXTRA_DOC);
        return DocRecordFragment.newInstance(doc);
    }
}
