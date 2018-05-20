package com.ruslan.pocketdoc.doc.docs;

import com.ruslan.pocketdoc.data.Doc;

import java.util.List;

public class DocsPresenterImpl implements DocsPresenter {

    private DocsView mDocsView;
    private DocsInteractor mDocsInteractor;

    private String mDocsSpecId;
    private String mDocsStationId;

    DocsPresenterImpl(DocsView docsView, DocsInteractor docsInteractor,
                      String docsSpecId, String docsStationId) {
        mDocsView = docsView;
        mDocsInteractor = docsInteractor;
        mDocsSpecId = docsSpecId;
        mDocsStationId = docsStationId;
    }

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onStop() {}

    @Override
    public void onDestroy() {
        mDocsView = null;
        mDocsInteractor = null;
    }

    @Override
    public void getData() {
        mDocsInteractor.loadDocs(new DocsInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Doc> docs) {
                if (mDocsView != null) {
                    mDocsView.showDocs(docs);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mDocsView != null) {
                    mDocsView.showLoadErrorMessage(throwable);
                }
            }
        }, mDocsSpecId, mDocsStationId);
    }
}
