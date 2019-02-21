package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.registration.RegistrationException
import technology.desoft.storekeeper.model.user.registration.RegistrationUser
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.navigation.navigations.LoginNavigation
import technology.desoft.storekeeper.presentation.view.RegistrationView
import technology.desoft.storekeeper.presentation.view.StartupView

@InjectViewState
class RegistrationPresenter(
    private val userRepository: UserRepository,
    private val router: Router<StartupView>
) : MvpPresenter<RegistrationView>() {

    private val jobs: MutableList<Job> = mutableListOf()

    fun register(rawEmail: String, rawUsername: String, rawPassword: String, isKeeper: Boolean){

        val email = rawEmail.trim()
        val username = rawUsername.trim()
        val password = rawPassword.trim()

        val registerJob = registerAsync(email, username, password, isKeeper)
        jobs.add(registerJob)
        registerJob.start()

    }

    private fun registerAsync(email: String, username: String, password: String, isKeeper: Boolean): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                tryRegister(email, username, password, isKeeper)
            } catch (e: RegistrationException){
                processError(e)
            }
        }
    }

    private suspend fun tryRegister(email: String, username: String, password: String, isKeeper: Boolean){
        val registrationUser = RegistrationUser(email, password, username, isKeeper)
        userRepository.registration(registrationUser)
        goToLogin()
    }

    private fun processError(e: RegistrationException){
        val errorJob = GlobalScope.launch(Dispatchers.Main){
            viewState.showError(e.message.toString())
        }
        jobs.add(errorJob)
        errorJob.start()
    }

    fun goToLogin(){
        router.navigate(LoginNavigation())
    }

    override fun onDestroy() {
        super.onDestroy()
        jobs.forEach(Job::cancel)
    }
}