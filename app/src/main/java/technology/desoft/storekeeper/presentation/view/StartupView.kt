package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView

interface StartupView: MvpView {
    fun showLogin()
    fun showRegistration()
    fun showUserScreen()
    fun showWatcherScreen()
}