package technology.desoft.storekeeper.model.room

interface RoomRepository {
    suspend fun getRooms(): List<Room>
}