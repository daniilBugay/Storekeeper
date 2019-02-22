package technology.desoft.storekeeper.model.user

interface UserProvider {
    fun loadUserEmail(): String?
    fun loadUserPassword(): String?

    fun saveUserEmail(email: String)
    fun saveUserPassword(password: String)

    fun clear()
}

fun UserProvider.saveEmailAndPassword(email: String, password: String) {
    saveUserEmail(email)
    saveUserPassword(password)
}

fun UserProvider.getEmailAndPassword(): Pair<String?, String?> {
    return loadUserEmail() to loadUserPassword()
}