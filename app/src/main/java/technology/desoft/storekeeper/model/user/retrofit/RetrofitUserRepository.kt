package technology.desoft.storekeeper.model.user.retrofit

import retrofit2.Retrofit
import technology.desoft.storekeeper.model.user.AuthResult
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.login.LoginException
import technology.desoft.storekeeper.model.user.registration.RegistrationException
import technology.desoft.storekeeper.model.user.registration.RegistrationUser

class RetrofitUserRepository(retrofit: Retrofit): UserRepository {
    private val api = retrofit.create(UserApi::class.java)

    override suspend fun login(loginUser: LoginUser): AuthResult {
        val response = api.login(loginUser).await()
        val body = response.body()
        if (response.isSuccessful && body != null)
            return body

        throw LoginException(response.message())
    }

    override suspend fun registration(registrationUser: RegistrationUser): AuthResult {
        val response = api.registration(registrationUser).await()
        val body = response.body()
        if (response.isSuccessful && body != null)
            return body

        throw RegistrationException(response.message())
    }
}