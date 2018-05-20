package com.ruslan.pocketdoc.doc.docs;

import com.ruslan.pocketdoc.data.Doc;

import java.util.List;

interface DocsView {
    void showDocs(List<Doc> docs);
    void showLoadErrorMessage(Throwable throwable);
}
