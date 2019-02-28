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
import technology.desoft.storekeeper.presentation.view.WatcherView
import java.io.IOException

@InjectViewState
class WatcherPresenter(
    private val itemRepository: ItemRepository,
    private val roomRepository: RoomRepository,
    private val userProvider: UserProvider
): MvpPresenter<WatcherView>() {

    private val jobs = mutableListOf<Job>()
    private var isRefreshing = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(){
        val showTypesJob = showTypes()
        jobs.add(showTypesJob)
        isRefreshing = true
        viewState.showLoading()
        showTypesJob.start()
    }

    private fun showTypes(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                val types = itemRepository.getItemTypes()
                launch(Dispatchers.Main) { viewState.showItemTypes(types) }
                types.firstOrNull()?.let { onItemTypeSelect(it) }
            } catch (e: IOException){
                isRefreshing = false
                launch(Dispatchers.Main) { processError(e) }
            }
        }
    }

    fun onItemTypeSelect(type: ItemType){
        val showItemsJob = showItemsWithType(type)
        jobs.add(showItemsJob)
        showItemsJob.start()
    }

    private fun showItemsWithType(itemType: ItemType): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            try {
                val roomsAndItems = loadItemsWithType(itemType)
                launch(Dispatchers.Main) { viewState.showItemsWithRoom(roomsAndItems) }
            } catch (e: IOException){
                launch(Dispatchers.Main) { processError(e)}
            } finally {
                isRefreshing = false
            }
        }
    }

    private suspend fun loadItemsWithType(itemType: ItemType): List<Pair<Room, Item>> {
        val rooms = roomRepository.getRooms()
        return rooms.map {
            it to itemRepository.getItemsFromRoom(it.id).first { item -> item.type == itemType.name }
        }
    }

    private fun processError(e: IOException){
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
        isRefreshing = true
        load()
    }
}