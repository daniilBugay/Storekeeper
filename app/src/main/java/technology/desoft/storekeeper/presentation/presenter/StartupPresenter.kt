package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import technology.desoft.storekeeper.navigation.Router
import technology.desoft.storekeeper.presentation.view.StartupView

@InjectViewState
class StartupPresenter(router: Router<StartupView>): MvpPresenter<StartupView>(){

    init {
        router.setView(viewState)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showLogin()
    }

    private fun showLogin(){
        viewState.showLogin()
    }

    private fun showRegistration(){
        viewState.showRegistration()
    }
}