package technology.desoft.storekeeper.model.user

data class RegistrationUser(
    val login: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean
)