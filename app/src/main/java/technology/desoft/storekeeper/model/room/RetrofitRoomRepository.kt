package technology.desoft.storekeeper.model.room

import android.content.res.Resources
import retrofit2.Retrofit
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.TOKEN_ERROR_MESSAGE
import technology.desoft.storekeeper.model.user.token.Token
import technology.desoft.storekeeper.model.user.token.TokenKeeper
import java.io.IOException
import java.net.UnknownHostException

class RetrofitRoomRepository(
    retrofit: Retrofit,
    private val tokenKeeper: TokenKeeper,
    private val resources: Resources
) : RoomRepository {
    private val api = retrofit.create(RoomApi::class.java)

    private fun getTokenAndUserId(): Pair<Token, Long> {
        val token = tokenKeeper.getToken() ?: throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        val userId = tokenKeeper.getUserId() ?: throw IllegalStateException(TOKEN_ERROR_MESSAGE)
        return token to userId
    }

    override suspend fun getRooms(): List<Room> {
        try {
            val (token, userId) = getTokenAndUserId()
            val response = api.getRooms(token.content, userId).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body

            throw IOException(response.message())
        } catch (e: UnknownHostException) {
            throw IOException(resources.getString(R.string.connection_error))
        }
    }
}