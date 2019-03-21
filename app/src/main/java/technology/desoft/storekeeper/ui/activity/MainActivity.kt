package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.MainPresenter
import technology.desoft.storekeeper.presentation.view.MainView
import technology.desoft.storekeeper.ui.changeFragment
import technology.desoft.storekeeper.ui.changeFragmentWithTransition
import technology.desoft.storekeeper.ui.fragment.*


class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return with(application as App) {
            MainPresenter(mainRouter, userRepository, userProvider, tokenKeeper)
        }
    }

    override fun showLogin() {
        val current = supportFragmentManager.fragments.firstOrNull()
        val loginFragment = LoginFragment()
        changeFragmentWithTransition(loginFragment) {
            val button = current?.view?.findViewById<View>(R.id.registerButton)
            val logo = current?.view?.findViewById<View>(R.id.registerLogo)
            if (button != null && logo != null) {
                addSharedElement(button, button.transitionName)
                addSharedElement(logo, logo.transitionName)
            }
            loginFragment.enterTransition = Fade()
        }
    }

    override fun showRegistration() {
        val current = supportFragmentManager.fragments.firstOrNull()
        val registerFragment = RegistrationFragment()
        changeFragmentWithTransition(registerFragment) {
            val button = current?.view?.findViewById<View>(R.id.loginButton)
            val logo = current?.view?.findViewById<View>(R.id.loginLogo)
            if (button != null && logo != null) {
                addSharedElement(button, button.transitionName)
                addSharedElement(logo, logo.transitionName)
            }
            registerFragment.enterTransition = Fade()
        }
    }

    override fun showUserScreen() {
        val userFragment = UserFragment()
        changeFragment(userFragment){
            userFragment.enterTransition = Explode()
        }
    }

    override fun showWatcherScreen() {
        val watcherFragment = WatcherFragment()
        changeFragment(watcherFragment){
            watcherFragment.enterTransition = Explode()
        }
    }

    override fun showSplashScreen() {
        val splashScreenFragment = SplashScreenFragment()
        changeFragment(splashScreenFragment) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}