package com.ruslanabzalov.pocketdoc.docs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

/**
 * Активность для хостинга фрагментов DocsTypesFragment и DocsMetrosFragment.
 */
public class DocsSearchParamsActivity extends SingleFragmentActivity {

    private static String mTargetFragmentName;

    /**
     * Метод, создающий интент для запуска активности DocsSearchParamsActivity.
     * @param packageContext контекст.
     * @param targetFragmentName имя нужного фрагмента.
     * @return Интент для запуска активности DocsSearchParamsActivity.
     */
    public static Intent newIntent(Context packageContext, String targetFragmentName) {
        mTargetFragmentName = targetFragmentName;
        Intent intent = new Intent(packageContext, DocsSearchParamsActivity.class);
        return intent;
    }

    /**
     * Метод, закрепляющий необходимый фрагмент.
     * @return Новый фрагмент.
     */
    @Override
    protected Fragment createFragment() {
        return (mTargetFragmentName.equals("DocsTypesFragment")
                ? new DocsTypesFragment()
                : new DocsMetrosFragment());
    }
}
