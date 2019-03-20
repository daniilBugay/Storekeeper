package technology.desoft.storekeeper.model.room

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RoomApi {
    @GET("/rooms")
    fun getRooms(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long
    ): Deferred<Response<Map<String, Room>>>
}