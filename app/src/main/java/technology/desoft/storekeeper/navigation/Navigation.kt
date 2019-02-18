package technology.desoft.storekeeper.navigation

import com.arellomobile.mvp.MvpView

interface Navigation<View: MvpView> {
    fun apply(view: View)
}