package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView

interface RegistrationView: MvpView {
    fun showError(message: String)
}