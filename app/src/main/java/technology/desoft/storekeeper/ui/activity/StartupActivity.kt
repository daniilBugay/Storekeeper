package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.StartupPresenter
import technology.desoft.storekeeper.presentation.view.StartupView
import technology.desoft.storekeeper.ui.changeFragmentWithTransition
import technology.desoft.storekeeper.ui.fragment.LoginFragment
import technology.desoft.storekeeper.ui.fragment.RegistrationFragment
import technology.desoft.storekeeper.ui.startActivity


class StartupActivity: MvpAppCompatActivity(), StartupView {

    @InjectPresenter
    lateinit var startupPresenter: StartupPresenter

    @ProvidePresenter
    fun providePresenter(): StartupPresenter {
        return with(application as App){
            StartupPresenter(startupRouter)
        }
    }

    override fun showLogin() {
        val current = supportFragmentManager.fragments.firstOrNull()
        val loginFragment = LoginFragment()
        changeFragmentWithTransition(loginFragment){
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
        changeFragmentWithTransition(registerFragment){
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
        startActivity<UserActivity>()
        finish()
    }

    override fun showWatcherScreen() {
        startActivity<WatcherActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
    }
}