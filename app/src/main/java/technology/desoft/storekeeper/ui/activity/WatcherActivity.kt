package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_watcher.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.presentation.presenter.WatcherPresenter
import technology.desoft.storekeeper.presentation.view.WatcherView
import technology.desoft.storekeeper.ui.adapter.ItemLeftAdapter
import technology.desoft.storekeeper.ui.adapter.RoomRightAdapter

class WatcherActivity : MvpAppCompatActivity(), WatcherView {

    @InjectPresenter
    lateinit var watcherPresenter: WatcherPresenter

    @ProvidePresenter
    fun providePresenter(): WatcherPresenter {
        return with(application as App){
            WatcherPresenter(itemRepository, roomRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcher)
        watcherLeftRecycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        watcherRightRecycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
    }

    override fun showItemTypes(itemTypes: List<ItemType>) {
        watcherLeftRecycler.adapter = ItemLeftAdapter(
            itemTypes = itemTypes,
            onItemClick = {watcherPresenter.onItemTypeSelect(it)}
        )
    }

    override fun showItemsWithRoom(roomsAndItems: List<Pair<Room, Item>>) {
        watcherRightRecycler.adapter = RoomRightAdapter(
            roomsAndItems = roomsAndItems,
            onItemStep = {watcherPresenter.onItemValueChange(it)}
        )
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
