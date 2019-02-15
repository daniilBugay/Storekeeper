package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.StartupPresenter
import technology.desoft.storekeeper.presentation.view.StartupView
import technology.desoft.storekeeper.ui.changeFragment
import technology.desoft.storekeeper.ui.fragment.LoginFragment
import technology.desoft.storekeeper.ui.fragment.RegistrationFragment
import technology.desoft.storekeeper.ui.startActivity

class StartupActivity: MvpAppCompatActivity(), StartupView {

    @InjectPresenter
    lateinit var startupPresenter: StartupPresenter

    override fun showLogin() {
        changeFragment(LoginFragment()){}
    }

    override fun showRegistration() {
        changeFragment(RegistrationFragment()){}
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