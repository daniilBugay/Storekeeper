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
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitUserRepository(retrofit: Retrofit, private val resources: Resources) : UserRepository {
    private val api = retrofit.create(UserApi::class.java)

    private companion object {
        const val REPEAT_COUNT = 3
    }

    override suspend fun login(loginUser: LoginUser): AuthResult {
        return loginWithRepeating(loginUser, REPEAT_COUNT)
    }

    private suspend fun loginWithRepeating(loginUser: LoginUser, repeatCount: Int): AuthResult {
        try {
            if (loginUser.email.isBlank() || loginUser.password.isBlank())
                throw LoginException(resources.getString(R.string.fields_empty_error))

            val response = api.login(loginUser).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body

            throw LoginException(resources.getString(R.string.invalid_login_error))
        } catch (e: SocketTimeoutException) {
            if (repeatCount > 0)
                return loginWithRepeating(loginUser, repeatCount - 1)

            throw LoginException(resources.getString(R.string.connection_error))
        } catch (e: UnknownHostException) {
            throw LoginException(resources.getString(R.string.connection_error))
        }
    }

    override suspend fun registration(registrationUser: RegistrationUser): AuthResult {
        return registerWithRepeating(registrationUser, REPEAT_COUNT)
    }

    private suspend fun registerWithRepeating(registrationUser: RegistrationUser, repeatCount: Int): AuthResult {
        try {
            if (
                registrationUser.email.isBlank() ||
                registrationUser.password.isBlank() ||
                registrationUser.username.isBlank()
            ) throw RegistrationException(resources.getString(R.string.fields_empty_error))

            val response = api.registration(registrationUser).await()
            val body = response.body()
            if (response.isSuccessful && body != null)
                return body

            throw RegistrationException(resources.getString(R.string.registration_error))
        } catch (e: SocketTimeoutException) {
            if (repeatCount > 0)
                return registerWithRepeating(registrationUser, repeatCount - 1)

            throw RegistrationException(resources.getString(R.string.connection_error))
        } catch (e: UnknownHostException) {
            throw RegistrationException(resources.getString(R.string.connection_error))
        }
    }


}