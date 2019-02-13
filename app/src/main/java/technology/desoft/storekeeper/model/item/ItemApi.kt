package technology.desoft.storekeeper.model.item

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ItemApi {
    @GET("items/")
    fun getItems(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long
    ): Deferred<Response<List<Item>>>

    @GET("ledgers/")
    fun getItemTypes(
        @Header("ACCESS_TOKEN") tokenContent: String,
        @Header("USER_ID") userId: Long
    ): Deferred<Response<List<ItemType>>>
}