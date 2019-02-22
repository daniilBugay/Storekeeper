package technology.desoft.storekeeper.model.user.token

interface TokenKeeper {
    fun getToken(): Token?
    fun setToken(token: Token)
    fun getUserId(): Long?
    fun setUserId(userId: Long)
}

fun TokenKeeper.setTokenAndUserId(tokenContent: String, userId: Long) {
    setToken(Token(tokenContent))
    setUserId(userId)
}