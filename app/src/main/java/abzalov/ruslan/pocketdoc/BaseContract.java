package abzalov.ruslan.pocketdoc;

import android.view.Menu;

/**
 * Интерфейс, описывающий основной контракт паттерна MVP.
 */
public interface BaseContract {

    interface BaseView {

        void setOptionsMenuVisible(Menu menu, boolean isVisible);

        void showErrorDialog(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void hideRefreshing();
    }

    interface BasePresenter<T extends BaseView> {

        void attachView(T t);

        void detachView();
    }
}
