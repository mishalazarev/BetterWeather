package white.ball.betterweather.presentation.view_model

import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.view_model.main_view_model.MainViewModel

class DetailScreenViewModel(
    cityName: MutableLiveData<String>,
    currentBackgroundColor: MutableLiveData<Brush>,
    currentWeatherInCity: MutableLiveData<WeatherInCityModel>,
    clickWeatherDayInCity: MutableLiveData<ClickWeatherDayInCityModel>,
    isOpenDialog: MutableLiveData<Boolean>,
) : MainViewModel(
    cityName = cityName,
    currentBackgroundColor = currentBackgroundColor,
    currentWeatherInCity = currentWeatherInCity,
    clickWeatherDayInCity = clickWeatherDayInCity,
    isOpenDialog = isOpenDialog
) {
}