package technology.desoft.storekeeper.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
): MvpPresenter<UserView>() {

    private val jobs = mutableListOf<Job>()
    private var isRefreshing = false
    private var lastSelectedRoom: Room? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(){
        val showTypesJob = showRooms()
        jobs.add(showTypesJob)
        showTypesJob.start()
    }

    private fun showRooms(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                val rooms = roomRepository.getRooms()
                launch(Dispatchers.Main) { viewState.showRooms(rooms) }
                if(lastSelectedRoom == null) onRoomSelected(rooms.first())
            } catch (e: IOException) {
                isRefreshing = false
                launch(Dispatchers.Main) { processError(e) }
            }
        }
    }

    fun onRoomSelected(room: Room) {
        GlobalScope.launch(Dispatchers.Main) {
            viewState.showLoading()
        }
        lastSelectedRoom = room
        val showItemsJob = showItemsWithRoom(room)
        jobs.add(showItemsJob)
        showItemsJob.start()
    }

    private fun showItemsWithRoom(room: Room): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                val itemsAndTypes = loadItemsAndType(room).sortedBy {it.first.id}
                launch(Dispatchers.Main) { viewState.showItemsWithType(room, itemsAndTypes) }
            } catch (e: IOException) {
                launch(Dispatchers.Main) { processError(e) }
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

    override fun onDestroy() {
        super.onDestroy()
        jobs.forEach(Job::cancel)
    }

    fun onItemValueChange(item: Item) {
        val changeItemJob = changeItem(item)
        jobs.add(changeItemJob)
        changeItemJob.start()
    }

    private fun changeItem(item: Item): Job {
        return GlobalScope.launch {
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