package technology.desoft.storekeeper.model.room

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("room_id")
    val id: Long,
    val number: String,
    @SerializedName("users_amount")
    val usersAmount: Int
)