package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

import java.util.UUID;

public class IllnessActivity extends SingleFragmentActivity {

    private static final String EXTRA_ILLNESS_ID =
            "com.ruslanabzalov.pocketdoc.illnesses.illness_id";

    static Intent newIntent(Context packageContext, UUID illnessId) {
        Intent intent = new Intent(packageContext, IllnessActivity.class);
        intent.putExtra(EXTRA_ILLNESS_ID, illnessId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID illnessId = (UUID) getIntent().getSerializableExtra(EXTRA_ILLNESS_ID);
        return IllnessFragment.newInstance(illnessId);
    }
}
