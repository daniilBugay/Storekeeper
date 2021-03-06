package technology.desoft.storekeeper.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_watcher.*
import kotlinx.android.synthetic.main.toolbar_default.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import kotlinx.android.synthetic.main.fragment_watcher.view.refreshProgressIndicator
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.presentation.presenter.WatcherPresenter
import technology.desoft.storekeeper.presentation.view.WatcherView
import technology.desoft.storekeeper.ui.activity.MainActivity
import technology.desoft.storekeeper.ui.adapter.ItemLeftAdapter
import technology.desoft.storekeeper.ui.adapter.RoomRightAdapter
import technology.desoft.storekeeper.ui.startActivity

class WatcherFragment : MvpAppCompatFragment(), WatcherView {

    @InjectPresenter
    lateinit var watcherPresenter: WatcherPresenter

    @ProvidePresenter
    fun providePresenter(): WatcherPresenter {
        return with(activity?.application as App){
            WatcherPresenter(itemRepository, roomRepository, userProvider)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        watcherLeftRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        watcherRightRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        logoutButton.setOnClickListener { watcherPresenter.onLogout() }
        refreshButton.setOnClickListener { watcherPresenter.refresh() }
    }

    override fun showItemTypes(itemTypes: List<ItemType>) {
        watcherLeftRecycler.adapter = ItemLeftAdapter(
            itemTypes = itemTypes,
            onItemClick = {watcherPresenter.onItemTypeSelect(it)}
        )
    }

    override fun showItemsWithRoom(itemType: ItemType, roomsAndItems: List<Pair<Room, Item>>) {
        watcherRightRecycler.adapter = RoomRightAdapter(
            roomsAndItems = roomsAndItems,
            onItemStep = {watcherPresenter.onItemValueChange(it)}
        )
        (watcherLeftRecycler.adapter as ItemLeftAdapter).setSelectedType(itemType)
        view?.refreshProgressIndicator?.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        view?.refreshProgressIndicator?.visibility = View.GONE
    }

    override fun logout() {
        activity?.startActivity<MainActivity>()
        activity?.finish()
    }

    override fun showLoading() {
        view?.refreshProgressIndicator?.visibility = View.VISIBLE
    }
}
