package technology.desoft.storekeeper.model.user

import technology.desoft.storekeeper.model.user.login.LoginUser
import technology.desoft.storekeeper.model.user.registration.RegistrationUser

interface UserRepository {
    suspend fun login(loginUser: LoginUser): AuthResult
    suspend fun registration(registrationUser: RegistrationUser): AuthResult
}