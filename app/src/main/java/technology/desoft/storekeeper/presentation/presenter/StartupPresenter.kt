package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.getEmailAndPassword
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import technology.desoft.storekeeper.model.user.token.setTokenAndUserId
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.navigation.navigations.UserScreenNavigation
import technology.desoft.storekeeper.navigation.navigations.WatcherScreenNavigation
import technology.desoft.storekeeper.presentation.view.StartupView

@InjectViewState
class StartupPresenter(
    router: Router<StartupView>,
    private val userRepository: UserRepository,
    private val provider: UserProvider,
    private val tokenKeeper: TokenKeeper
): MvpPresenter<StartupView>(){

    private val jobs = mutableListOf<Job>()

    init {
        router.setView(viewState)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val (email, password) = provider.getEmailAndPassword()
        if (email != null && password != null) {
            val loginJob = loginAsync(email, password)
            jobs.add(loginJob)
            loginJob.start()
            return
        }
        showLogin()
    }

    private fun loginAsync(email: String, password: String): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            val result = userRepository.login(LoginUser(email, password))
            tokenKeeper.setTokenAndUserId(result.tokenContent, result.userId)
            if (result.isKeeper)
                viewState.showWatcherScreen()
            else
                viewState.showUserScreen()
        }
    }

    private fun showLogin(){
        viewState.showLogin()
    }

    private fun showRegistration(){
        viewState.showRegistration()
    }

    override fun onDestroy() {
        super.onDestroy()
        jobs.forEach(Job::cancel)
    }
}