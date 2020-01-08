package abzalov.ruslan.pocketdoc

import android.view.Menu

/**
 * Интерфейс, описывающий основной контракт.
 */
interface BaseContract {

    interface BaseView {

        fun setOptionsMenuVisible(menu: Menu, isVisible: Boolean)

        fun showErrorDialog(throwable: Throwable)

        fun showProgressBar()

        fun hideProgressBar()

        fun hideRefreshing()
    }

    interface BasePresenter<in T : BaseView> {

        fun attachView(t: T)

        fun detachView()
    }
}