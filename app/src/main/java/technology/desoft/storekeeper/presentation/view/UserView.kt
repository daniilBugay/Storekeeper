package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room

interface UserView: MvpView {
    fun showError(message: String)
    fun showItemsWithType(itemsAndTypes: List<Pair<Item, ItemType>>)
    fun showRooms(rooms: List<Room>)
}