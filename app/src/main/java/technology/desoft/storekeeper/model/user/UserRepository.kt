package technology.desoft.storekeeper.model.user

interface UserRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun registration(registrationUser: RegistrationUser): RegistrationResult
}