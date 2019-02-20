package technology.desoft.storekeeper.model.item

import android.content.res.Resources
import retrofit2.Retrofit
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.TOKEN_ERROR_MESSAGE
import technology.desoft.storekeeper.model.user.token.Token
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import java.io.IOException
import java.net.UnknownHostException

class RetrofitItemRepository(
    retrofit: Retrofit,
    private val tokenKeeper: TokenKeeper,
    private val resources: Resources
) : ItemRepository {
    private val api = retrofit.create(ItemApi::class.java)

    private fun getTokenAndUserId(): Pair<Token, Long> {
        val token = tokenKeeper.getToken() ?: throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        val userId = tokenKeeper.getUserId() ?: throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        return token to userId
    }

    override suspend fun getItemsFromRoom(roomId: Long): List<Item> {
        try {
            val (token, userId) = getTokenAndUserId()
            val response = api.getItems(token.content, userId).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body

            throw IOException(response.message())
        } catch (e: UnknownHostException) {
            throw IOException(resources.getString(R.string.connection_error))
        }
    }

    override suspend fun changeItemAmount(item: Item, newAmount: Double) {
        try {
            val response = api.changeItem(item.copy(amount = newAmount)).await()
            if (!response.isSuccessful)
                throw IllegalAccessException(resources.getString(R.string.change_item_error))
        } catch (e: UnknownHostException){
            throw IOException(resources.getString(R.string.connection_error))
        }
    }

    override suspend fun getItemTypes(): List<ItemType> {
        try {
            val (token, userId) = getTokenAndUserId()
            val response = api.getItemTypes(token.content, userId).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body

            throw IOException(response.message())
        } catch (e: UnknownHostException) {
            throw IOException(resources.getString(R.string.connection_error))
        }
    }
}