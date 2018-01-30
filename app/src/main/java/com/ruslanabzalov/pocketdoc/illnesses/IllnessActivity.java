package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

import java.util.UUID;

/**
 * Активность для хостинга фрагмента IllnessFragment.
 */
public class IllnessActivity extends SingleFragmentActivity {

    /**
     * Ключ дополнения, являющегося идентификатором экземпляра класса Illness.
     */
    private static final String EXTRA_ILLNESS_ID =
            "com.ruslanabzalov.pocketdoc.illnesses.illness_id";

    /**
     * Статический метод, создающий новый интент с дополнениями
     * для запуска активности IllnessActivity.
     * @param packageContext контекст
     * @param illnessId идентификатор заболевания
     * @return интент с дополнениями для запуска активности IllnessActivity
     */
    static Intent newIntent(Context packageContext, UUID illnessId) {
        Intent intent = new Intent(packageContext, IllnessActivity.class);
        intent.putExtra(EXTRA_ILLNESS_ID, illnessId);
        return intent;
    }

    /**
     * Метод, создающий фрагмент IllnessFragment и передающий ему дополнение,
     * полученное от фрагмента IllnessesListFragment.
     * @return фрагмент IllnessFragment с аргументами
     */
    @Override
    protected Fragment createFragment() {
        UUID illnessId = (UUID) getIntent().getSerializableExtra(EXTRA_ILLNESS_ID);
        return IllnessFragment.newInstance(illnessId);
    }
}
