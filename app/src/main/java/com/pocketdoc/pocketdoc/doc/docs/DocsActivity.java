package com.pocketdoc.pocketdoc.doc.docs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.pocketdoc.pocketdoc.SingleFragmentActivity;

/**
 * Активность-хост для фрагмента DocsFragment.
 */
public class DocsActivity extends SingleFragmentActivity {

    private static final String EXTRA_SPEC_ID = "spec_id";
    private static final String EXTRA_STATION_ID = "station_id";

    /**
     * Статический метод для создания объекта Intent
     * с дополнениями для запуска активности DocsActivity.
     * @param context Контекст.
     * @param specId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @return Интент для запуска активности DocsActivity.
     */
    public static Intent newIntent(Context context, String specId, String stationId) {
        Intent intent = new Intent(context, DocsActivity.class);
        intent.putExtra(EXTRA_SPEC_ID, specId);
        intent.putExtra(EXTRA_STATION_ID, stationId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String specId = getIntent().getStringExtra(EXTRA_SPEC_ID);
        String stationId = getIntent().getStringExtra(EXTRA_STATION_ID);
        return DocsFragment.newInstance(specId, stationId);
    }
}
