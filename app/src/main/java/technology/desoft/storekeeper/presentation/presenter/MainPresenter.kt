package technology.desoft.storekeeper.presentation.presenter

import android.view.ViewTreeObserver
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.getEmailAndPassword
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import technology.desoft.storekeeper.model.user.token.setTokenAndUserId
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.presentation.view.MainView

@InjectViewState
class MainPresenter(
    router: Router<MainView>,
    private val userRepository: UserRepository,
    private val provider: UserProvider,
    private val tokenKeeper: TokenKeeper
): CoroutineUserPresenter<MainView>(){

    init {
        router.setView(viewState)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val (email, password) = provider.getEmailAndPassword()
        if (email != null && password != null) {
            loginAsync(email, password)
            return
        }
        showLogin()
    }

    private fun loginAsync(email: String, password: String) {
        background.launch {
            showSplashScreen()
            val result = userRepository.login(LoginUser(email, password))
            tokenKeeper.setTokenAndUserId(result.tokenContent, result.userId)
            if (result.isKeeper)
                showWatcherScreen()
            else
                showUserScreen()
        }
    }

    private fun showLogin(){
        viewState.showLogin()
    }

    private suspend fun showWatcherScreen(){
        withContext(ui.coroutineContext) {
            viewState.showWatcherScreen()
        }
    }

    private suspend fun showUserScreen(){
        withContext(ui.coroutineContext){
            viewState.showUserScreen()
        }
    }

    private suspend fun showSplashScreen(){
        withContext(ui.coroutineContext){
            viewState.showSplashScreen()
        }
    }
}