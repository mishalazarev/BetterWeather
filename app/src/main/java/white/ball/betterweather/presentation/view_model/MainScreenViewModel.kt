package white.ball.betterweather.presentation.view_model

import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import white.ball.betterweather.data.api.WeatherRetrofitService
import white.ball.betterweather.domain.model.Forecastday
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.presentation.view_model.main_view_model.MainViewModel

class MainScreenViewModel(
    cityName: MutableLiveData<String>,
    currentBackgroundColor: MutableLiveData<Brush>,
    currentWeatherInCity: MutableLiveData<WeatherInCity>,
    clickForecastDayModel: MutableLiveData<Forecastday>,
    isOpenDialog: MutableLiveData<Boolean>,
    apiService: WeatherRetrofitService,
) : MainViewModel(
    cityName = cityName,
    currentBackgroundColor = currentBackgroundColor,
    currentWeatherInCity = currentWeatherInCity,
    clickForecastDayModel = clickForecastDayModel,
    isOpenDialog = isOpenDialog,
    apiService = apiService,
) {
}