package abzalov.ruslan.pocketdoc;

import android.view.Menu;

public interface BaseContract {

    interface BaseView {

        void setOptionsMenuVisible(Menu menu, boolean isVisible);

        void showErrorDialog(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void hideRefreshing();
    }

    interface BasePresenter<T extends BaseView> {

        void attachView(T view);

        void detachView();
    }
}
