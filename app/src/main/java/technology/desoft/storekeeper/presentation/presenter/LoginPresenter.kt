package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.login.LoginException
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.saveEmailAndPassword
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import technology.desoft.storekeeper.model.user.token.setTokenAndUserId
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.navigation.navigations.RegistrationNavigation
import technology.desoft.storekeeper.navigation.navigations.UserScreenNavigation
import technology.desoft.storekeeper.navigation.navigations.WatcherScreenNavigation
import technology.desoft.storekeeper.presentation.view.LoginView
import technology.desoft.storekeeper.presentation.view.MainView

@InjectViewState
class LoginPresenter(
    private val userRepository: UserRepository,
    private val tokenKeeper: TokenKeeper,
    private val userProvider: UserProvider,
    private val router: Router<MainView>
) : CoroutineUserPresenter<LoginView>() {

    private var isLogin = false

    fun login(rawEmail: String, rawPassword: String) {
        if (isLogin) return
        isLogin = true
        val email = rawEmail.trim()
        val password = rawPassword.trim()

        loginAsync(email, password)
    }

    private fun loginAsync(email: String, password: String) {
        background.launch {
            try {
                tryLogin(email, password)
            } catch (e: LoginException){
                processError(e)
            }
        }
    }

    private suspend fun tryLogin(email: String, password: String) {
        val loginUser = LoginUser(email, password)
        val loginResult = userRepository.login(loginUser)
        userProvider.saveEmailAndPassword(email, password)
        tokenKeeper.setTokenAndUserId(loginResult.tokenContent, loginResult.userId)
        isLogin = false
        if (loginResult.isKeeper)
            showWatcherScreen()
        else
            showUserScreen()
    }

    private suspend fun showWatcherScreen() {
        withContext(ui.coroutineContext) {
            router.navigate(WatcherScreenNavigation())
        }
    }

    private suspend fun showUserScreen() {
        withContext(ui.coroutineContext) {
            router.navigate(UserScreenNavigation())
        }
    }

    private suspend fun processError(e: LoginException) {
        withContext(ui.coroutineContext){
            viewState.showError(e.message.toString())
        }
        isLogin = false
    }

    fun goToRegistration(){
        router.navigate(RegistrationNavigation())
    }
}