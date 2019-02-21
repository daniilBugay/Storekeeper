package technology.desoft.storekeeper.model.item

interface ItemRepository {
    suspend fun getItemsFromRoom(roomId: Long): List<Item>
    suspend fun changeItem(item: Item)
    suspend fun getItemTypes(): List<ItemType>
}