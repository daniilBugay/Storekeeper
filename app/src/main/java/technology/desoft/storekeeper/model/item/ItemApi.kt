package technology.desoft.storekeeper.model.item

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ItemApi {
    @GET("items/")
    fun getItems(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long
    ): Deferred<Response<Map<String, Item>>>

    @PUT("items/")
    fun changeItem(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long,
        @Body changedItem: Item
    ): Deferred<Response<Item>>

    @GET("ledgers/")
    fun getItemTypes(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long
    ): Deferred<Response<Map<String,ItemType>>>

    @GET("rooms/{id}")
    fun getItemsFromRoom(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long,
        @Path("id") roomId: Long
    ): Deferred<Response<Map<String,Item>>>

    @GET("ledgers/{id}")
    fun getItemsWithType(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long,
        @Path("id") typeId: Long
    ): Deferred<Response<Map<String,Item>>>
}