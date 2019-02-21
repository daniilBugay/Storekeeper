package technology.desoft.storekeeper.model.user.registration

import com.google.gson.annotations.SerializedName

data class RegistrationUser(
    val email: String,
    val password: String,
    @SerializedName("name")
    val username: String,
    @SerializedName("is_keeper")
    val isKeeper: Boolean
)