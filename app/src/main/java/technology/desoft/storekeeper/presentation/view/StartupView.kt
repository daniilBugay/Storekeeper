package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface StartupView: MvpView {
    fun showLogin()
    fun showRegistration()
    fun showUserScreen()
    fun showWatcherScreen()
}