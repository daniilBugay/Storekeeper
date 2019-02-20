package technology.desoft.storekeeper.model.item

interface ItemRepository {
    suspend fun getItemsFromRoom(roomId: Long): List<Item>
    suspend fun changeItemAmount(item: Item, newAmount: Double)
    suspend fun getItemTypes(): List<ItemType>
}