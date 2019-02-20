package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.navigation.navigations.LoginNavigation
import technology.desoft.storekeeper.presentation.view.RegistrationView
import technology.desoft.storekeeper.presentation.view.StartupView

@InjectViewState
class RegistrationPresenter(
    private val userRepository: UserRepository,
    private val router: Router<StartupView>
) : MvpPresenter<RegistrationView>() {

    fun goToLogin(){
        router.navigate(LoginNavigation())
    }
}