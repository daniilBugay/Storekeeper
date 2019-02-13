package technology.desoft.storekeeper.model.user.token

class SimpleTokenKeeper: TokenKeeper {
    private var token: Token? = null

    override fun getToken() = token

    override fun setToken(token: Token){
        this.token = token
    }
}