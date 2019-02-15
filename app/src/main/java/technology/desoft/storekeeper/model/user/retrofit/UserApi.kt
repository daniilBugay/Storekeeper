package technology.desoft.storekeeper.model.user.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import technology.desoft.storekeeper.model.user.AuthResult
import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.registration.RegistrationUser

interface UserApi {
    @POST("users/login")
    fun login(@Body loginUser: LoginUser): Deferred<Response<AuthResult>>
    @POST("users/sign_up")
    fun registration(@Body registrationUser: RegistrationUser): Deferred<Response<AuthResult>>
}