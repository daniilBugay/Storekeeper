package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.registration.RegistrationException
import technology.desoft.storekeeper.model.user.registration.RegistrationUser
import technology.desoft.storekeeper.model.user.saveEmailAndPassword
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import technology.desoft.storekeeper.model.user.token.setTokenAndUserId
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.navigation.navigations.LoginNavigation
import technology.desoft.storekeeper.navigation.navigations.UserScreenNavigation
import technology.desoft.storekeeper.navigation.navigations.WatcherScreenNavigation
import technology.desoft.storekeeper.presentation.view.RegistrationView
import technology.desoft.storekeeper.presentation.view.MainView

@InjectViewState
class RegistrationPresenter(
    private val userRepository: UserRepository,
    private val tokenKeeper: TokenKeeper,
    private val userProvider: UserProvider,
    private val router: Router<MainView>
) : CoroutineUserPresenter<RegistrationView>() {

    private var isRegistration = false

    fun register(rawEmail: String, rawUsername: String, rawPassword: String, isKeeper: Boolean) {
        if (isRegistration) return

        isRegistration = true
        val email = rawEmail.trim()
        val username = rawUsername.trim()
        val password = rawPassword.trim()

        registerAsync(email, username, password, isKeeper)
    }

    private fun registerAsync(email: String, username: String, password: String, isKeeper: Boolean) {
        background.launch {
            try {
                tryRegister(email, username, password, isKeeper)
            } catch (e: RegistrationException) {
                processError(e)
            }
        }
    }

    private suspend fun tryRegister(email: String, username: String, password: String, isKeeper: Boolean) {
        val registrationUser = RegistrationUser(email, password, username, isKeeper)
        val registerResult = userRepository.registration(registrationUser)
        userProvider.saveEmailAndPassword(email, password)
        tokenKeeper.setTokenAndUserId(registerResult.tokenContent, registerResult.userId)
        isRegistration = false
        if (registerResult.isKeeper)
            router.navigate(WatcherScreenNavigation())
        else
            router.navigate(UserScreenNavigation())
    }

    private suspend fun processError(e: RegistrationException) {
        withContext(ui.coroutineContext){
            viewState.showError(e.message.toString())
        }
        isRegistration = false
    }

    fun goToLogin() {
        router.navigate(LoginNavigation())
    }
}