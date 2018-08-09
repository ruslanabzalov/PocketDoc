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
         * Метод отображения экземпляра <code>DialogFragment</code>
         * с информацией об ошибке получения данных.
         * @param throwable Исключение, полученное в результате ошибки.
         */
        void showErrorDialog(Throwable throwable);

        /**
         * Метод отображения <code>ProgressBar</code>.
         */
        void showProgressBar();

        /**
         * Метод сокрытия <code>ProgressBar</code>.
         */
        void hideProgressBar();

        /**
         * Метод сокрытия иконки загрузки <code>SwipeRefreshLayout</code>.
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
