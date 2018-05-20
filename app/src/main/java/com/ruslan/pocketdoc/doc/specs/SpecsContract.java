package com.ruslan.pocketdoc.doc.specs;

import com.ruslan.pocketdoc.data.Spec;

import java.util.List;

interface SpecsContract {

    interface MvpView {
        void showSpecs(List<Spec> specs);
        void showLoadErrorMessage(Throwable throwable);
    }
    
    interface MvpPresenter {
        void onDestroy();
        void getData();
    }

    interface MvpInteractor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Spec> specs);
            void onFailure(Throwable throwable);
        }

        void loadSpecs(OnLoadFinishedListener onLoadFinishedListener);
    }
}
