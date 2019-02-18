package technology.desoft.storekeeper.navigation

import com.arellomobile.mvp.MvpView

interface Router<View: MvpView> {
    fun setView(view: View)
    fun navigate(navigation: Navigation<View>)
}