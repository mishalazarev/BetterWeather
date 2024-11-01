package white.ball.betterweather.presentation

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import white.ball.betterweather.data.api.WeatherRetrofitService
import white.ball.betterweather.data.storage.LocalStorage
import white.ball.betterweather.domain.model.Forecastday
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80

class App : Application() {

    private var retrofit: Retrofit? = getRetrofit()
    private val localStorage = LocalStorage()

    val apiService: WeatherRetrofitService by lazy {
        retrofit!!.create(WeatherRetrofitService::class.java)
    }

    var cityName = MutableLiveData("")
    var currentWeatherInCity = MutableLiveData<WeatherInCity>()
    var currentForecastDay = MutableLiveData<Forecastday>()
    val isOpenDialog = MutableLiveData(false)

    val currentBackgroundColor =
        MutableLiveData(Brush.sweepGradient(colors = listOf(Purple80, Pink80)))

    override fun onCreate() {
        super.onCreate()
        localStorage.initPreference(getSharedPreferences(FILE_NAME_SHARED_PREFERENCE, MODE_PRIVATE))
        cityName.value = localStorage.readNameCity()

        getRetrofit()

//        currentWeatherInCity.value = apiService.getWeatherInCity().body()

//        Log.d(TAG, "MainNavController before: ${apiService.getWeatherInCity("Rostov-Na-Donu")}")
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return checkNotNull(retrofit)
    }

    companion object {
        @JvmStatic
        val FILE_NAME_SHARED_PREFERENCE = "local_storage_file"

        @JvmStatic
        val BASE_URL_WEATHER = "https://api.weatherapi.com/"

        @JvmStatic
        val API_KEY = "5ab316c38faa4d789b8193014241108"


//        fun createDefaultWeatherModel() = WeatherInCityModel(
//            "Rostov-Na-Donu", "", "00:00", "", "",
//            "0", "0", "0", "0",
//            "00:00", "00:00",
//            "00:00", "00:00",
//            mutableListOf(),
//            mutableListOf()
//        )
//
//        fun createDefaultClickOtherDayWeatherModel() = ClickWeatherDayInCityModel(
//            "", "", "", "",
//            "0", "0",
//            "00:00", "00:00",
//            "00:00", "00:00",
//            mutableListOf()
//        )
    }
}

