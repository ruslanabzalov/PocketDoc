package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private SpecialitiesContract.View mView;

    private Disposable mDisposable;

    @Inject
    Repository mRepository;

    public SpecialitiesPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(SpecialitiesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mDisposable.dispose();
    }

    @Override
    public void loadSpecialities() {
        mView.showProgressBar();
        mDisposable = mRepository.getSpecsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        specialities -> {
                            if (mView != null) {
                                mView.showSpecialities(specialities);
                                mView.hideProgressBar();
                            }
                        }
                );
//        mDisposable = mRepository.getSpecialities(false)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(SpecialityList::getSpecialities)
//                .doOnNext(specialities -> mRepository.saveSpecialities(specialities))
//                .subscribe(
//                        specialities -> {
//                            if (mView != null) {
//                                mView.showSpecialities(specialities);
//                                mView.hideProgressBar();
//                            }
//                        },
//                        throwable -> {
//                            if (mView != null) {
//                                mView.showErrorMessage(throwable);
//                                mView.hideProgressBar();
//                            }
//                        }
//                );
    }

    @Override
    public void updateSpecialities(boolean isMenuRefreshing) {
//        if (isMenuRefreshing) {
//            mView.showProgressBar();
//            mRepository.getSpecialities(true)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(SpecialityList::getSpecialities)
//                    .doOnNext(specialities -> mRepository.saveSpecialities(specialities))
//                    .subscribe(new Observer<List<Speciality>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {}
//
//                        @Override
//                        public void onNext(List<Speciality> specialities) {
//                            if (mView != null) {
//                                mView.showSpecialities(specialities);
//                                mView.hideProgressBar();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            if (mView != null) {
//                                mView.showErrorMessage(e);
//                                mView.hideProgressBar();
//                            }
//                        }
//
//                        @Override
//                        public void onComplete() {}
//                    });
//        } else {
//            mRepository.getSpecialities(true)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<SpecialityList>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {}
//
//                        @Override
//                        public void onNext(SpecialityList specialityList) {
//                            if (mView != null) {
//                                mView.showSpecialities(specialityList.getSpecialities());
//                                mView.hideRefreshing();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            if (mView != null) {
//                                mView.showErrorMessage(e);
//                                mView.hideRefreshing();
//                            }
//                        }
//
//                        @Override
//                        public void onComplete() {}
//                    });
//        }
    }

    @Override
    public void openRecordsHistory() {
        mView.showRecordsHistoryListUi();
    }

    @Override
    public void chooseSpeciality(Speciality speciality) {
        mView.showStationListUi(speciality.getId());
    }
}
