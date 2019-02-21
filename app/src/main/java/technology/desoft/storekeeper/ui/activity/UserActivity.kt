package technology.desoft.storekeeper.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_user.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.presentation.presenter.UserPresenter
import technology.desoft.storekeeper.presentation.view.UserView
import technology.desoft.storekeeper.ui.adapter.ItemRightAdapter
import technology.desoft.storekeeper.ui.adapter.RoomLeftAdapter

class UserActivity : MvpAppCompatActivity(), UserView {

    @InjectPresenter
    lateinit var userPresenter: UserPresenter

    @ProvidePresenter
    fun providePresenter(): UserPresenter {
        return with(application as App){
            UserPresenter(itemRepository, roomRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userLeftRecycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        userRightRecycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
    }

    override fun showRooms(rooms: List<Room>) {
        userLeftRecycler.adapter = RoomLeftAdapter(
            rooms = rooms,
            onItemClick = {userPresenter.onRoomSelected(it)}
        )
    }

    override fun showItemsWithType(itemsAndTypes: List<Pair<Item, ItemType>>) {
        userRightRecycler.adapter = ItemRightAdapter(
            itemsAndTypes = itemsAndTypes,
            onItemStep = {userPresenter.onItemValueChange(it)}
        )
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
