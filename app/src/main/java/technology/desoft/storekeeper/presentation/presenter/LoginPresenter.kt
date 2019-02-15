package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.login.LoginException
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.token.Token
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import technology.desoft.storekeeper.presentation.view.LoginView

@InjectViewState
class LoginPresenter(
    private val userRepository: UserRepository,
    private val tokenKeeper: TokenKeeper,
    private val userProvider: UserProvider
) : MvpPresenter<LoginView>() {

    private val jobs: MutableList<Job> = mutableListOf()

    fun login(rawEmail: String, rawPassword: String) {
        val email = rawEmail.trim()
        val password = rawPassword.trim()

        val loginJob = loginAsync(email, password)
        jobs.add(loginJob)
        loginJob.start()
    }

    private fun loginAsync(email: String, password: String): Job {
        return GlobalScope.launch(Dispatchers.IO) {
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
        saveEmailAndPassword(email, password)
        setTokenAndUserId(loginResult.tokenContent, loginResult.userId)
    }

    private fun saveEmailAndPassword(email: String, password: String) {
        userProvider.saveUserEmail(email)
        userProvider.saveUserPassword(password)
    }

    private fun setTokenAndUserId(tokenContent: String, userId: Long) {
        tokenKeeper.setToken(Token(tokenContent))
        tokenKeeper.setUserId(userId)
    }

    private fun processError(e: LoginException) {
        val errorJob = GlobalScope.launch(Dispatchers.Main) {
            viewState.showError(e.message.toString())
        }
        jobs.add(errorJob)
        errorJob.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        jobs.forEach(Job::cancel)
    }
}