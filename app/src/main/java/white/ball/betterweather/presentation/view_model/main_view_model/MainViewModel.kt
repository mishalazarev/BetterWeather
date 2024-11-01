package white.ball.betterweather.presentation.view_model.main_view_model

import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import white.ball.betterweather.data.api.WeatherRetrofitService
import white.ball.betterweather.data.storage.LocalStorage
import white.ball.betterweather.domain.model.Forecastday
import white.ball.betterweather.domain.model.WeatherInCity

abstract class MainViewModel(
    cityName: MutableLiveData<String>,
    currentBackgroundColor: MutableLiveData<Brush>,
    currentWeatherInCity: MutableLiveData<WeatherInCity>,
    clickForecastDayModel: MutableLiveData<Forecastday>,
    isOpenDialog: MutableLiveData<Boolean>,
    private val apiService: WeatherRetrofitService,
    ) : ViewModel() {

    private val _mCityName = MutableLiveData("")
    val mCityName: LiveData<String> = _mCityName

    private val _mWeatherInCity = MutableLiveData<WeatherInCity>()
    val mWeatherInCity: LiveData<WeatherInCity> = _mWeatherInCity

    private val _mClickWeatherDayInCity = MutableLiveData<Forecastday>()
    val mClickWeatherDayInCity: LiveData<Forecastday> = _mClickWeatherDayInCity

    private val _mBackgroundColor = MutableLiveData<Brush>()
    val mBackgroundColor: LiveData<Brush> = _mBackgroundColor

    val _mIsOpenDialog = MutableLiveData(false)
    val mIsOpenDialog: LiveData<Boolean> = _mIsOpenDialog

    private val localStorage = LocalStorage()

    init {
        _mCityName.value = cityName.value
        _mBackgroundColor.value = currentBackgroundColor.value
        _mWeatherInCity.value = currentWeatherInCity.value
        _mClickWeatherDayInCity.value = clickForecastDayModel.value
        _mIsOpenDialog.value = isOpenDialog.value
    }

    fun setClickWeatherDayInCity(indexDay: Int) {
        _mClickWeatherDayInCity.value = checkNotNull(_mWeatherInCity.value).forecast.forecastday[indexDay]
    }

    fun setOpenDialog() {
        _mIsOpenDialog.value = !_mIsOpenDialog.value!!
    }

    suspend fun fetchWeatherData() {
        _mWeatherInCity.value = apiService.getWeatherInCity(nameCity = checkNotNull(_mCityName.value))
    }

    suspend fun fetchWeatherData(
        newCityName: String,
    ) {
        _mCityName.value = newCityName
        _mWeatherInCity.value = apiService.getWeatherInCity(nameCity = newCityName)
        localStorage.writeNameCity(checkNotNull(_mCityName.value))
    }
}