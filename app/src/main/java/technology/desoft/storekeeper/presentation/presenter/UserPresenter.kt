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
import technology.desoft.storekeeper.presentation.view.UserView
import java.io.IOException

@InjectViewState
class UserPresenter(
    private val itemRepository: ItemRepository,
    private val roomRepository: RoomRepository,
    private val userProvider: UserProvider
): CoroutineUserPresenter<UserView>() {

    private var isRefreshing = false
    private var lastSelectedRoom: Room? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(){
        showRooms()
    }

    private fun showRooms(): Job {
        return background.launch {
            try {
                val rooms = roomRepository.getRooms()
                withContext(ui.coroutineContext) { viewState.showRooms(rooms) }
                if(lastSelectedRoom == null) onRoomSelected(rooms.first())
            } catch (e: IOException) {
                isRefreshing = false
                withContext(ui.coroutineContext) { processError(e) }
            }
        }
    }

    fun onRoomSelected(room: Room) {
        ui.launch { viewState.showLoading() }
        lastSelectedRoom = room
        showItemsWithRoom(room)
    }

    private fun showItemsWithRoom(room: Room) {
        background.launch {
            try {
                val itemsAndTypes = loadItemsAndType(room).sortedBy {it.second.name}
                withContext(ui.coroutineContext) { viewState.showItemsWithType(room, itemsAndTypes) }
            } catch (e: IOException) {
                withContext(ui.coroutineContext) { processError(e) }
            } finally {
                isRefreshing = false
            }
        }
    }

    private suspend fun loadItemsAndType(room: Room): List<Pair<Item, ItemType>> {
        val items = itemRepository.getItemsFromRoom(room.id)
        val types = itemRepository.getItemTypes()
        return items.map { item ->
            val itemTypeName = types.find { it.id == item.typeId }?.name
            item to types.first {
                type -> type.name == itemTypeName
            }
        }
    }

    private fun processError(e: IOException) {
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
        lastSelectedRoom?.let { onRoomSelected(it) }
        isRefreshing = true
        viewState.showLoading()
    }
}