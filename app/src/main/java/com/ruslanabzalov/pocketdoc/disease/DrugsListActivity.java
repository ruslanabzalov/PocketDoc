package com.ruslanabzalov.pocketdoc.disease;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DrugsListActivity extends SingleFragmentActivity {

    /**
     * Метод, создающий интент для запуска активности DrugsListActivity.
     * @param packageContext контекст.
     * @return интент для запуска активности DrugsListActivity.
     */
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, DrugsListActivity.class);
    }

    /**
     * Метод, создающий фрагмент DrugListFragment.
     * @return фрагмент DrugListFragment.
     */
    @Override
    protected Fragment createFragment() {
        return new DrugsListFragment();
    }
}
