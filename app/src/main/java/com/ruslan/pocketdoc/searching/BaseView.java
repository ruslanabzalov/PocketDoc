package com.ruslan.pocketdoc.searching;

import java.util.List;

public interface BaseView<T> {

    void showItems(List<T> t);

    void showErrorMessage(Throwable throwable);
}
