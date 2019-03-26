package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemRepository
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.model.room.RoomRepository
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.presentation.view.WatcherView
import java.io.IOException

@InjectViewState
class WatcherPresenter(
    private val itemRepository: ItemRepository,
    private val roomRepository: RoomRepository,
    private val userProvider: UserProvider
): CoroutineUserPresenter<WatcherView>() {

    private var isRefreshing = false
    private var lastSelectedType: ItemType? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(){
        showTypes()
        isRefreshing = true
    }

    private fun showTypes() {
        background.launch {
            try {
                val types = itemRepository.getItemTypes()
                withContext(ui.coroutineContext) { viewState.showItemTypes(types) }
                if (lastSelectedType == null) onItemTypeSelect(types.first())
            } catch (e: IOException){
                isRefreshing = false
                withContext(ui.coroutineContext) { processError(e) }
            }
        }
    }

    fun onItemTypeSelect(type: ItemType){
        ui.launch(Dispatchers.Main) {
            viewState.showLoading()
        }
        lastSelectedType = type
        showItemsWithType(type)
    }

    private fun showItemsWithType(itemType: ItemType) {
        background.launch {
            try {
                val roomsAndItems = loadItemsWithType(itemType).sortedBy { it.second.name }
                withContext(ui.coroutineContext) { viewState.showItemsWithRoom(itemType, roomsAndItems) }
            } catch (e: IOException){
                withContext(ui.coroutineContext) { processError(e) }
            } finally {
                isRefreshing = false
            }
        }
    }

    private suspend fun loadItemsWithType(itemType: ItemType): List<Pair<Room, Item>> {
        val rooms = roomRepository.getRooms()
        val itemsWithType = itemRepository.getItemsWithType(itemType)
        return rooms.map { room ->
            room to itemsWithType.first { item ->
                item.roomId == room.id
            }
        }
    }

    private fun processError(e: IOException){
        viewState.showError(e.message.toString())
    }

    fun onItemValueChange(item: Item) {
        changeItem(item)
    }

    private fun changeItem(item: Item) {
        background.launch {
            itemRepository.changeItem(item)
        }
    }

    fun onLogout(){
        userProvider.clear()
        viewState.logout()
    }

    fun refresh() {
        if (isRefreshing) return
        isRefreshing = true
        lastSelectedType?.let { onItemTypeSelect(it) }
        viewState.showLoading()
    }
}