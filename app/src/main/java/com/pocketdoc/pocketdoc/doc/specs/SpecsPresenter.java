package com.pocketdoc.pocketdoc.doc.specs;

import com.pocketdoc.pocketdoc.data.Spec;

import java.util.List;

public class SpecsPresenter implements SpecsContract.Presenter {

    private SpecsContract.MainView mMainView;
    private SpecsContract.Fetcher mFetcher;

    SpecsPresenter(SpecsContract.MainView mainView, SpecsContract.Fetcher fetcher) {
        mMainView = mainView;
        mFetcher = fetcher;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void getData() {
        mFetcher.getSpecs(new SpecsContract.Fetcher.OnFetchListener() {
            @Override
            public void onSuccess(List<Spec> specs) {
                if (mMainView != null) {
                    mMainView.setDataToRecyclerView(specs);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mMainView != null) {
                    mMainView.onResponseFailureMessage(throwable);
                }
            }
        });
    }
}
