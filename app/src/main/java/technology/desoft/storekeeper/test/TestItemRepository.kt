package technology.desoft.storekeeper.test

import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemRepository
import technology.desoft.storekeeper.model.item.ItemType
import java.util.concurrent.TimeUnit

class TestItemRepository : ItemRepository {
    private val items = mutableListOf(
        Item("Cookies", "Cookies", 1, 99.0),
        Item("Cookies", "Cookies", 2, 10.0),
        Item("Cookies", "Cookies", 3, 1.0)
    )

    override suspend fun getItemsFromRoom(roomId: Long): List<Item> {
        TimeUnit.SECONDS.sleep(1)
        return items
    }

    override suspend fun changeItem(item: Item) {

    }

    override suspend fun getItemTypes(): List<ItemType> {
        TimeUnit.SECONDS.sleep(1)
        val image = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Oxygen480-apps-preferences-web-browser-cookies.svg/1024px-Oxygen480-apps-preferences-web-browser-cookies.svg.png"

        return List(10) {
            ItemType(it.toLong(), "Cookies", image)
        }
    }
}