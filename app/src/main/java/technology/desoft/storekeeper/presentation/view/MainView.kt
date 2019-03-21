package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface MainView: MvpView {
    fun showLogin()
    fun showRegistration()
    fun showUserScreen()
    fun showWatcherScreen()
    fun showSplashScreen()
}