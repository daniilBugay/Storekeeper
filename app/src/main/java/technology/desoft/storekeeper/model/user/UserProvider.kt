package technology.desoft.storekeeper.model.user

interface UserProvider {
    fun loadUserEmail(): String?
    fun loadUserPassword(): String?

    fun saveUserEmail(email: String)
    fun saveUserPassword(password: String)

    fun clear()
}