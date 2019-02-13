package technology.desoft.storekeeper.model.user

import com.google.gson.annotations.SerializedName

data class AuthResult(
    val name: String,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("is_keeper")
    val isKeeper: Boolean,
    @SerializedName("auth_token")
    val tokenContent: String
)