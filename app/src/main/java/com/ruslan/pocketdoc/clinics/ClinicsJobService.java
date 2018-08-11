package com.ruslan.pocketdoc.clinics;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClinicsJobService extends JobService {

    private static final String TAG = "ClinicsJobService";

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "Job: Started!");
        App.getComponent().inject(this);
        mDisposable = mRepository.getClinicsFromApi()
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getClinicsFromApi(): onSubscribe()"))
                .doOnNext(clinics -> {
                    Log.i(TAG, "getClinicsFromApi(): onNext()");
                    Log.i(TAG, "Clinics loaded: " + clinics.size());
                    jobFinished(jobParameters, false);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getClinicsFromApi(): onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    jobFinished(jobParameters, true);
                })
                .doOnComplete(() -> Log.i(TAG, "getClinicsFromApi(): onComplete()"))
                .subscribeOn(Schedulers.io())
                .subscribe();
        return true;
    }

    /*
    Почему-то вылетает приложение, если во время загрузки списка клиник включить авиарежим!
     */

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "Job: Stopped!");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
