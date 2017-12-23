package com.ruslanabzalov.pocketdoc.disease;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

import java.util.UUID;

/**
 * Активность для хостинга фрагмента DiseaseFragment.
 */
public class DiseaseActivity extends SingleFragmentActivity {

    /**
     * Ключ для доступа к дополнению-идентификатору экземпляра класса Disease.
     */
    private static final String EXTRA_DISEASE_ID = "com.ruslanabzalov.pocketdoc.disease.disease_id";

    /**
     * Статический метод, создающий новый интент с дополнениями
     * для запуска активности DiseaseActivity.
     * @param packageContext контекст
     * @param diseaseId ID заболевания
     * @return интент с дополнениями для запуска активности DiseaseActivity
     */
    public static Intent newIntent(Context packageContext, UUID diseaseId) {
        Intent intent = new Intent(packageContext, DiseaseActivity.class);
        intent.putExtra(EXTRA_DISEASE_ID, diseaseId);
        return intent;
    }

    /**
     * Метод, создающий фрагмент DiseaseFragment и передающий ему дополнения,
     * полученные от активности MainActivity.
     * @return фрагмент DiseaseFragment с аргументами
     */
    @Override
    protected Fragment createFragment() {
        UUID diseaseId = (UUID) getIntent().getSerializableExtra(EXTRA_DISEASE_ID);
        return DiseaseFragment.newInstance(diseaseId);
    }
}
