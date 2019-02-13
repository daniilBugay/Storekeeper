package technology.desoft.storekeeper

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import technology.desoft.storekeeper.model.user.PreferenceUserProvider
import technology.desoft.storekeeper.model.user.UserProvider
import technology.desoft.storekeeper.model.user.UserRepository
import technology.desoft.storekeeper.model.user.retrofit.RetrofitUserRepository
import technology.desoft.storekeeper.model.user.token.SimpleTokenKeeper
import technology.desoft.storekeeper.model.user.token.TokenKeeper

class App: Application() {
    lateinit var userRepository: UserRepository
    lateinit var tokenKeeper: TokenKeeper
    lateinit var userProvider: UserProvider

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getString(R.string.base_url))
            .build()

        userRepository = RetrofitUserRepository(retrofit)
        tokenKeeper = SimpleTokenKeeper()
        userProvider = PreferenceUserProvider(PreferenceManager.getDefaultSharedPreferences(this))
    }
}