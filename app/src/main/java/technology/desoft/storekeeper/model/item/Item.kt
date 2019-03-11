package technology.desoft.storekeeper.model.item

import com.google.gson.annotations.SerializedName

data class Item(
    val name: String,
    @SerializedName("item_type")
    val type: String,
    @SerializedName("room_id")
    val roomId: Long,
    var amount: Double
)