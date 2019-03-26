package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class CoroutineUserPresenter<View: MvpView>: MvpPresenter<View>() {
    private val job = SupervisorJob()
    protected val ui = CoroutineScope(Dispatchers.Main + job)
    protected val background = CoroutineScope(Dispatchers.IO + job)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}