package technology.desoft.storekeeper.test

import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemRepository
import technology.desoft.storekeeper.model.item.ItemType

class TestItemRepository : ItemRepository {
    private val items = mutableListOf(
        Item("Cookies", "Cookies", 1, 100.0),
        Item("Cookies", "Cookies", 2, 10.0),
        Item("Cookies", "Cookies", 3, 1.0)
    )

    override suspend fun getItemsFromRoom(roomId: Long): List<Item> {
        return items
    }

    override suspend fun changeItem(item: Item) {

    }

    override suspend fun getItemTypes(): List<ItemType> {
        val image = "http://www.pngall.com/wp-content/uploads/2016/07/Cookie-PNG.png"

        return List(10) {
            ItemType(it.toLong(), "Cookies", image)
        }
    }
}