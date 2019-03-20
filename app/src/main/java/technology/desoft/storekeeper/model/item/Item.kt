package technology.desoft.storekeeper.model.item

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("item_id")
    val id: Long,
    val name: String,
    @SerializedName("item_type_id")
    val typeId: Long,
    @SerializedName("room_id")
    val roomId: Long,
    var amount: Double
)