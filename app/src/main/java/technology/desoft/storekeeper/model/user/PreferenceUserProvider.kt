package technology.desoft.storekeeper.model.user

import android.content.SharedPreferences

class PreferenceUserProvider(private val sharedPreferences: SharedPreferences): UserProvider {
    private companion object {
        const val USER_EMAIL_KEY = "email"
        const val USER_PASSWORD_KEY = "password"
    }

    override fun loadUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL_KEY, null)
    }

    override fun loadUserPassword(): String? {
        return sharedPreferences.getString(USER_PASSWORD_KEY, null)
    }

    override fun saveUserEmail(email: String) {
        sharedPreferences.edit()
            .putString(USER_EMAIL_KEY, email)
            .apply()
    }

    override fun saveUserPassword(password: String) {
        sharedPreferences.edit()
            .putString(USER_PASSWORD_KEY, password)
            .apply()
    }

    override fun clear() {
        sharedPreferences.edit()
            .remove(USER_PASSWORD_KEY)
            .remove(USER_EMAIL_KEY)
            .apply()
    }

}