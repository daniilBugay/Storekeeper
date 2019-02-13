package technology.desoft.storekeeper.model.item

import retrofit2.Retrofit
import technology.desoft.storekeeper.model.TOKEN_ERROR_MESSAGE
import technology.desoft.storekeeper.model.user.token.Token
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import java.io.IOException

class RetrofitItemRepository(retrofit: Retrofit, private val tokenKeeper: TokenKeeper)
    :ItemRepository {
    private val api = retrofit.create(ItemApi::class.java)

    private fun getTokenAndUserId(): Pair<Token, Long> {
        val token = tokenKeeper.getToken() ?:
            throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        val userId = tokenKeeper.getUserId() ?:
            throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        return token to userId
    }

    override suspend fun getItemsFromRoom(roomId: Long): List<Item> {
        val (token, userId) = getTokenAndUserId()
        val response = api.getItems(token.content, userId).await()
        val body = response.body()
        if (response.isSuccessful && body != null)
            return body

        throw IOException(response.message())
    }

    override suspend fun getItemTypes(): List<ItemType> {
        val (token, userId) = getTokenAndUserId()
        val response = api.getItemTypes(token.content, userId).await()
        val body = response.body()
        if (response.isSuccessful && body != null)
            return body

        throw IOException(response.message())
    }
}