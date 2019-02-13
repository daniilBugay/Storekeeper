package technology.desoft.storekeeper.model.user.token

class SimpleTokenKeeper: TokenKeeper {
    private var token: Token? = null
    private var userId: Long? = null

    override fun getToken() = token

    override fun setToken(token: Token){
        this.token = token
    }

    override fun getUserId() = userId

    override fun setUserId(userId: Long) {
        this.userId = userId
    }
}