package technology.desoft.storekeeper.model.user.token

interface TokenKeeper {
    fun getToken(): Token?
    fun setToken(token: Token)
}