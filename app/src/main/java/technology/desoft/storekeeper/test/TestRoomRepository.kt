package technology.desoft.storekeeper.test

import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.model.room.RoomRepository

class TestRoomRepository: RoomRepository {
    override suspend fun getRooms(): List<Room> {
        return listOf(
            Room(1, "303"),
            Room(2, "408"),
            Room(3, "409"),
            Room(4, "410"),
            Room(5, "411"),
            Room(6, "412"),
            Room(7, "413"),
            Room(8, "414"),
            Room(9, "415")
        )
    }
}