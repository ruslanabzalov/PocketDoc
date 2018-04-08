package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocRecordActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOC = "com.ruslanabzalov.pocketdoc.docs.doc";

    public static Intent newIntent(Context packageContext, Doctor doctor) {
        Intent intent = new Intent(packageContext, DocRecordActivity.class);
        intent.putExtra(EXTRA_DOC, doctor);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Doctor doctor = (Doctor) getIntent().getSerializableExtra(EXTRA_DOC);
        return DocRecordFragment.newInstance(doctor);
    }
}
