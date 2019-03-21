package technology.desoft.storekeeper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_login.view.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.LoginPresenter
import technology.desoft.storekeeper.presentation.view.LoginView

class LoginFragment: MvpAppCompatFragment(), LoginView {
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return with(activity?.application as App) {
            LoginPresenter(userRepository, tokenKeeper, userProvider, mainRouter)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.loginButton.setOnClickListener { login(view) }
        view.registerLabel.setOnClickListener { loginPresenter.goToRegistration() }
    }

    private fun login(view: View) {
        showProgress()
        val email = view.loginEmail.text.toString()
        val password = view.loginPassword.text.toString()
        loginPresenter.login(email, password)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        hideProgress()
    }

    private fun showProgress(){
        view?.loginProgressIndicator?.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        view?.loginProgressIndicator?.visibility = View.GONE
    }
}