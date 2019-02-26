package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserView: MvpView {
    fun showError(message: String)
    fun showItemsWithType(itemsAndTypes: List<Pair<Item, ItemType>>)
    fun showRooms(rooms: List<Room>)
    fun logout()
}