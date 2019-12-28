package abzalov.ruslan.pocketdoc.clinic;

import android.util.Log;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClinicPresenter implements ClinicContract.Presenter {

    private static final String TAG = ClinicPresenter.class.getSimpleName();

    private ClinicContract.View mView;

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    ClinicPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(ClinicContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadClinicInfo(int clinicId) {
        mDisposable = mRepository.getClinicByIdFromDb(clinicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> Log.i(TAG, "getClinicByIdFromDb(): onSubscribe()"))
                .doOnSuccess(clinic -> {
                    Log.i(TAG, "getClinicByIdFromDb(): onSuccess()");
                    if (mView != null) {
                        mView.showClinicInfo(clinic);
                    }
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "getClinicByIdFromDb(): onError()", throwable);
                    if (mView != null) {
                        // TODO: Показать сообщение об ошибке.
                    }
                })
                .subscribe();
    }
}
