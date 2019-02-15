package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.presentation.view.RegistrationView

@InjectViewState
class RegistrationPresenter(private val userRepository: UserRepository) : MvpPresenter<RegistrationView>() {
}