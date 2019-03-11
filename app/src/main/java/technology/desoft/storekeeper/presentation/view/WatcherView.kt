package technology.desoft.storekeeper.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room

@StateStrategyType(AddToEndSingleStrategy::class)
interface WatcherView: MvpView {
    fun showItemTypes(itemTypes: List<ItemType>)
    fun showError(message: String)
    fun showItemsWithRoom(itemType: ItemType, roomsAndItems: List<Pair<Room, Item>>)
    fun showLoading()
    fun logout()
}