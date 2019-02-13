package technology.desoft.storekeeper.model.room

import retrofit2.Retrofit
import technology.desoft.storekeeper.model.TOKEN_ERROR_MESSAGE
import technology.desoft.storekeeper.model.user.token.Token
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import java.io.IOException

class RetrofitRoomRepository(retrofit: Retrofit, private val tokenKeeper: TokenKeeper): RoomRepository {
    private val api = retrofit.create(RoomApi::class.java)

    private fun getTokenAndUserId(): Pair<Token, Long> {
        val token = tokenKeeper.getToken() ?:
        throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        val userId = tokenKeeper.getUserId() ?:
        throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        return token to userId
    }

    override suspend fun getRooms(): List<Room> {
        val (token, userId) = getTokenAndUserId()
        val response = api.getRooms(token.content, userId).await()
        val body = response.body()
        if (response.isSuccessful && body != null)
            return body

        throw IOException(response.message())
    }
}