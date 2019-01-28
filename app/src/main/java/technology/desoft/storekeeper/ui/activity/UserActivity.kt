package technology.desoft.storekeeper.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.UserPresenter
import technology.desoft.storekeeper.presentation.view.UserView

class UserActivity : MvpAppCompatActivity(), UserView {

    lateinit var userPresenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
    }
}
