package com.ruslan.pocketdoc.doc.docs;

public interface DocsPresenter {
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
    void getData();
}
