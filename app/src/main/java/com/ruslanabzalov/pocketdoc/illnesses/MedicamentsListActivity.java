package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class MedicamentsListActivity extends SingleFragmentActivity {

    /**
     * Метод, создающий интент для запуска активности MedicamentsListActivity.
     * @param packageContext контекст.
     * @return интент для запуска активности MedicamentsListActivity.
     */
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MedicamentsListActivity.class);
    }

    /**
     * Метод, создающий фрагмент DrugListFragment.
     * @return фрагмент DrugListFragment.
     */
    @Override
    protected Fragment createFragment() {
        return new MedicamentsListFragment();
    }
}
