package technology.desoft.storekeeper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            RegistrationPresenter(userRepository, startupRouter)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.loginLabel.setOnClickListener { registrationPresenter.goToLogin() }
        view.registerButton.setOnClickListener { registration() }
    }

    override fun showError(message: String) {
        hideProgress()
    }

    private fun registration(){
        showProgress()
        
    }

    private fun showProgress(){
        view?.registerProgressIndicator?.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        view?.registerProgressIndicator?.visibility = View.GONE
    }
}