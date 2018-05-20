package com.ruslan.pocketdoc.doc.docs;

import com.ruslan.pocketdoc.data.Doc;

import java.util.List;

public interface DocsInteractor {

    interface OnLoadFinishedListener {
        void onSuccess(List<Doc> docs);
        void onFailure(Throwable throwable);
    }

    void loadDocs(OnLoadFinishedListener onLoadFinishedListener, String specId, String stationId);
}
