package technology.desoft.storekeeper.model.item

import com.google.gson.annotations.SerializedName

data class ItemType(
    @SerializedName("item_type_id")
    val id: Long,
    val name: String,
    @SerializedName("image_url")
    val image: String
)