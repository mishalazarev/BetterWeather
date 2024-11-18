package white.ball.betterweather.presentation.view_model

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import white.ball.betterweather.data.api.APIService
import white.ball.betterweather.data.local_storage.LocalStorage
import white.ball.betterweather.domain.model.ClickedWeatherInCity
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80

class MainScreenViewModel(app: Application) : AndroidViewModel(app) {

    private val _weatherInCity = MutableLiveData<WeatherInCity>()
    val weatherInCity: LiveData<WeatherInCity> = _weatherInCity

    private val _clickedWeatherInCity = MutableLiveData<ClickedWeatherInCity>()
    val clickedWeatherInCity: LiveData<ClickedWeatherInCity> = _clickedWeatherInCity

    private val _backgroundColor = MutableLiveData(
        Brush.sweepGradient(
            colors = listOf(Purple80, Pink80)
        )
    )
    val backgroundColor: LiveData<Brush> = _backgroundColor

    val context = app.applicationContext

    val apiService = APIService()
    val localStorage =
        LocalStorage(context.getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, MODE_PRIVATE))

    fun initInformation() {
        val readNameCity = localStorage.readNameCity()

        apiService.getMainJSONObject(
            nameCity = readNameCity,
            context = context,
            weatherInCity = _weatherInCity,
            backgroundColor = _backgroundColor
        )
    }

    fun changeCity(newNameCity: String) {
        apiService.getMainJSONObject(
            nameCity = newNameCity,
            context = context,
            weatherInCity = _weatherInCity,
            backgroundColor = _backgroundColor
        )
        localStorage.writeNameCity(newNameCity = newNameCity)
    }

    fun setClickedWeatherInCity(indexDay: Int) {
        _clickedWeatherInCity.value = checkNotNull(_weatherInCity.value).weatherByWeekList[indexDay]
    }

    private companion object {
        const val NAME_FILE_SHARED_PREFERENCE = "local_storage"
    }
}