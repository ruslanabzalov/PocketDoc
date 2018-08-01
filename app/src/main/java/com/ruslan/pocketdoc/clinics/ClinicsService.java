package com.ruslan.pocketdoc.clinics;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class ClinicsService extends IntentService {

    private static final String TAG = "ClinicsService";

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, ClinicsService.class);
    }

    public ClinicsService() {
        super(TAG);
        App.getComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (isNetworkAvailableAndConnected()) {
            mDisposable = mRepository.getClinicsFromApi()
                    .subscribe(clinics -> Toast.makeText(getApplicationContext(),
                            "Clinics size: " + clinics.size(),
                            Toast.LENGTH_SHORT).show(),
                            throwable -> Toast.makeText(getApplicationContext(),
                                    "Throwable message: " + throwable.getMessage(),
                                    Toast.LENGTH_SHORT).show()
                    );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**
     * Метод проверки доступности сетевого соединения.
     * @return Флаг статуса сетевого соединения.
     */
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
        return isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
