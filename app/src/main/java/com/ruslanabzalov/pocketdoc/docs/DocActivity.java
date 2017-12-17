package com.ruslanabzalov.pocketdoc.docs;

import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

/**
 * Активность, отвечающая за хостинг фрагмента DocFragment.
 */
public class DocActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return DocFragment.newInstance();
    }
}
