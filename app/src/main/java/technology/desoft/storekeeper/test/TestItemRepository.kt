package technology.desoft.storekeeper.test

import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemRepository
import technology.desoft.storekeeper.model.item.ItemType
import java.util.concurrent.TimeUnit

class TestItemRepository : ItemRepository {
    private val items = mutableListOf(
        Item("Cookies", "Cookies", 1, 100.0),
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
        val image = "https://png.pngtree.com/element_pic/17/03/22/2de2d82b4a67578ba9e51a91ed394cb2.jpg"

        return List(10) {
            ItemType(it.toLong(), "Cookies", image)
        }
    }
}