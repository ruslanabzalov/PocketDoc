package com.ruslan.pocketdoc.searching;

import java.util.List;

public interface BaseView<T> {

    void showList(List<T> t);

    void showLoadErrorMessage(Throwable throwable);
}
