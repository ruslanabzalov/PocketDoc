package com.pocketdoc.pocketdoc.doc.specs;

import com.pocketdoc.pocketdoc.data.Spec;

import java.util.List;

interface SpecsContract {

    interface Presenter {
        void onDestroy();
        void getData();
    }

    interface MainView {
        void setDataToRecyclerView(List<Spec> specs);
        void onResponseFailureMessage(Throwable throwable);
    }

    interface Fetcher {

        interface OnFetchListener {
            void onSuccess(List<Spec> specs);
            void onFailure(Throwable throwable);
        }

        void getSpecs(OnFetchListener onFetchListener);
    }
}
