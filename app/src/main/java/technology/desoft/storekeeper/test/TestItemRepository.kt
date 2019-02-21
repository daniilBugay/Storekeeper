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
        val image = "https://www.andrey-andreev.com/wp-content/uploads/2018/03/Funny-Cookies-Sweetness-Joker-Delicious-Food-3114974.jpg"

        return List(10) {
            ItemType(it.toLong(), "Cookies", image)
        }
    }
}