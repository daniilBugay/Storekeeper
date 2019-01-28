package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.presentation.presenter.WatcherPresenter
import technology.desoft.storekeeper.presentation.view.WatcherView

class WatcherActivity : MvpAppCompatActivity(), WatcherView {

    @InjectPresenter
    lateinit var watcherPresenter: WatcherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
    }
}
