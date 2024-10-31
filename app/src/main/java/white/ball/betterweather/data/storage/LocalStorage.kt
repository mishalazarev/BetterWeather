package white.ball.betterweather.data.storage

import android.content.SharedPreferences

class LocalStorage {

    private lateinit var sharedPreference: SharedPreferences


    fun initPreference(newSharedPreferences: SharedPreferences) {
        sharedPreference = newSharedPreferences
    }

    fun writeNameCity(newNameCity: String) {
        with(sharedPreference.edit()) {
            putString(NAME_CITY_KEY, newNameCity)
            commit()
        }
    }

    fun readNameCity(): String = sharedPreference.getString(NAME_CITY_KEY, "") ?: ""

    private companion object {
        @JvmStatic
        val NAME_CITY_KEY = "name_city_key"
    }
}