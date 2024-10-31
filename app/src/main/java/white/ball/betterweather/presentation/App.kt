package white.ball.betterweather.presentation

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import white.ball.betterweather.data.ApiService
import white.ball.betterweather.data.storage.LocalStorage
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80

class App : Application(){

    private val apiService = ApiService()
    private val localStorage = LocalStorage()

    var cityName = MutableLiveData("")
    var currentWeatherInCity = MutableLiveData(createDefaultWeatherModel())
    var clickOtherDayWeather = MutableLiveData(createDefaultClickOtherDayWeatherModel())
    var currentResponse = MutableLiveData("")
    val isOpenDialog = MutableLiveData(false)

    val currentBackgroundColor = MutableLiveData(Brush.sweepGradient(colors = listOf(Purple80, Pink80)))

    override fun onCreate() {
        super.onCreate()

        localStorage.initPreference(getSharedPreferences(FILE_NAME_SHARED_PREFERENCE, MODE_PRIVATE))
        cityName.value = localStorage.readNameCity()

        apiService.getMainJSONObject(
            nameCity = cityName,
            currentResponse = currentResponse,
            context = this,
            currentWeatherInPlace = currentWeatherInCity,
            currentBackgroundColor = currentBackgroundColor
        )

        Log.d(TAG, "MainNavController before: ${currentWeatherInCity.value!!.sunsetTime}")
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private companion object {
        @JvmStatic
        val FILE_NAME_SHARED_PREFERENCE = "local_storage_file"

        fun createDefaultWeatherModel() = WeatherInCityModel(
            "Rostov-Na-Donu", "", "00:00", "", "",
            "0", "0", "0", "0",
            "00:00", "00:00",
            "00:00", "00:00",
            mutableListOf(),
            mutableListOf()
        )

        fun createDefaultClickOtherDayWeatherModel() = ClickWeatherDayInCityModel(
            "", "", "", "",
            "0", "0",
            "00:00", "00:00",
            "00:00", "00:00",
            mutableListOf()
        )
    }
}

