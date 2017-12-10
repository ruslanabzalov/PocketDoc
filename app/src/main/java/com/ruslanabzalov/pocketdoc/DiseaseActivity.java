package com.ruslanabzalov.pocketdoc;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Активность для хостинга фрагментов DiseaseFragment.
 * */
public class DiseaseActivity extends SingleFragmentActivity {

    /**
     * Константа-ключ для доступа к дополнению-идентификатору экземпляра класса Disease
     * */
    private static final String EXTRA_DISEASE_ID = "com.ruslanabzalov.pocketdoc.disease_id";

    /**
     * Статический метод для создания нового интента с дополнениями.
     * */
    public static Intent newIntent(Context packageContext, UUID diseaseId) {
        // Создание нового интента
        Intent intent = new Intent(packageContext, DiseaseActivity.class);
        // Добавление дополнения в интент
        intent.putExtra(EXTRA_DISEASE_ID, diseaseId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID diseaseId = (UUID) getIntent().getSerializableExtra(EXTRA_DISEASE_ID);
        return DiseaseFragment.newInstance(diseaseId);
    }
}
