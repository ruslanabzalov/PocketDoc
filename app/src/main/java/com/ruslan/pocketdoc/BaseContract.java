package com.ruslan.pocketdoc;

/**
 * Базовый интерфейс, описывающий контракт между базовым View и базовым Presenter.
 */
public interface BaseContract {

    /**
     * Интерфейс, описывающий базовый View.
     */
    interface BaseView {

        /**
         * Метод отображения DialogFragment с информацией об ошибке.
         * @param throwable Исключение, полученное в результате ошибки.
         */
        void showErrorDialog(Throwable throwable);

        /**
         * Метод отображения ProgressBar.
         */
        void showProgressBar();

        /**
         * Метод сокрытия ProgressBar.
         */
        void hideProgressBar();

        /**
         * Метод сокрытия иконки загрузки SwipeRefreshLayout.
         */
        void hideRefreshing();
    }

    /**
     * Интерфейс, описывающий базовый Presenter.
     * @param <V> Тип привязываемого View.
     */
    interface BasePresenter<V extends BaseView> {

        /**
         * Метод привязки View.
         * @param view View, который необходимо привязать к Presenter.
         */
        void attachView(V view);

        /**
         * Метод отвязки View.
         */
        void detachView();
    }
}
