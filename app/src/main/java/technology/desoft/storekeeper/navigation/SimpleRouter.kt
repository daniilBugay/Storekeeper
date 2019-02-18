package technology.desoft.storekeeper.navigation

import com.arellomobile.mvp.MvpView

class SimpleRouter<View: MvpView>: Router<View> {
    private var view: View? = null

    override fun setView(view: View) {
        this.view = view
    }

    override fun navigate(navigation: Navigation<View>) {
        view?.let { navigation.apply(it) }
    }
}