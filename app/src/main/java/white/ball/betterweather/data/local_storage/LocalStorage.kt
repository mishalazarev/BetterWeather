package white.ball.betterweather.data.local_storage

import android.content.SharedPreferences

class LocalStorage(
    newSharedPreferences: SharedPreferences
) {

    private var sharedPreferences: SharedPreferences = newSharedPreferences

    fun readNameCity(): String = sharedPreferences.getString(NAME_CITY_KEY, DEFAULT_NAME_CITY).toString()

    fun writeNameCity(newNameCity: String) {
        with(sharedPreferences.edit()) {
            putString(NAME_CITY_KEY, newNameCity)
            commit()
        }
    }

    companion object {
        private const val NAME_CITY_KEY = "name_city_key"
        private const val DEFAULT_NAME_CITY = "Rostov-Na-Donu"
    }
}