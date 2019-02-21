package technology.desoft.storekeeper.model.user.retrofit

import android.content.res.Resources
import retrofit2.Retrofit
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.user.AuthResult
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.login.LoginException
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.registration.RegistrationException
import technology.desoft.storekeeper.model.user.registration.RegistrationUser
import java.net.UnknownHostException

class RetrofitUserRepository(retrofit: Retrofit, private val resources: Resources) : UserRepository {
    private val api = retrofit.create(UserApi::class.java)

    override suspend fun login(loginUser: LoginUser): AuthResult {
        try {
            val response = api.login(loginUser).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body
            throw LoginException(resources.getString(R.string.invalid_login_error))
        } catch (e: UnknownHostException) {
            throw LoginException(resources.getString(R.string.connection_error))
        }
    }

    override suspend fun registration(registrationUser: RegistrationUser): AuthResult {
        try {
            val response = api.registration(registrationUser).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body
            throw RegistrationException(resources.getString(R.string.registration_error))
        } catch (e: UnknownHostException) {
            throw RegistrationException(resources.getString(R.string.connection_error))
        }
    }
}