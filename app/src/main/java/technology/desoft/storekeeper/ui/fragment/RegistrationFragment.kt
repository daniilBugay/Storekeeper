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
import kotlinx.android.synthetic.main.fragment_registration.view.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.RegistrationPresenter
import technology.desoft.storekeeper.presentation.view.RegistrationView

class RegistrationFragment: MvpAppCompatFragment(), RegistrationView {
    @InjectPresenter
    lateinit var registrationPresenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter {
        return with(activity?.application as App){
            RegistrationPresenter(userRepository, tokenKeeper, userProvider, startupRouter)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.loginLabel.setOnClickListener { registrationPresenter.goToLogin() }
        view.registerButton.setOnClickListener { registration(view) }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        hideProgress()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun registration(view: View){
        showProgress()
        val email = view.registrationEmail.text.toString()
        val username = view.registrationUsername.text.toString()
        val password = view.registrationPassword.text.toString()
        val isKeeper = view.checkbox.isChecked
        registrationPresenter.register(email, username, password, isKeeper)

    }

    private fun showProgress(){
        view?.registerProgressIndicator?.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        view?.registerProgressIndicator?.visibility = View.GONE
    }
}