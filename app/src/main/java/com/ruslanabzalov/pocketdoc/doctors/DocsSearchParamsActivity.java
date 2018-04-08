package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DocsSearchParamsActivity extends SingleFragmentActivity {

    private static String mTargetFragmentName;

    public static Intent newIntent(Context packageContext, String targetFragmentName) {
        mTargetFragmentName = targetFragmentName;
        Intent intent = new Intent(packageContext, DocsSearchParamsActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return (mTargetFragmentName.equals("DocsTypesFragment")
                ? new DocsTypesFragment()
                : new DocsMetrosFragment());
    }
}
