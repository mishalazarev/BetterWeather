package white.ball.betterweather.presentation.view_model.main_view_model

import android.content.Context
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import white.ball.betterweather.data.ApiService
import white.ball.betterweather.data.storage.LocalStorage
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel

abstract class MainViewModel(
    cityName: MutableLiveData<String>,
    currentBackgroundColor: MutableLiveData<Brush>,
    currentWeatherInCity: MutableLiveData<WeatherInCityModel>,
    clickWeatherDayInCity: MutableLiveData<ClickWeatherDayInCityModel>,
    isOpenDialog: MutableLiveData<Boolean>,
    ) : ViewModel() {

    private val _mCityName = MutableLiveData("")
    val mCityName: LiveData<String> = _mCityName

    private val _mWeatherInCity = MutableLiveData<WeatherInCityModel>()
    val mWeatherInCity: LiveData<WeatherInCityModel> = _mWeatherInCity

    private val _mClickWeatherDayInCity = MutableLiveData<ClickWeatherDayInCityModel>()
    val mClickWeatherDayInCity: LiveData<ClickWeatherDayInCityModel> = _mClickWeatherDayInCity

    private val _mBackgroundColor = MutableLiveData<Brush>()
    val mBackgroundColor: LiveData<Brush> = _mBackgroundColor

    private val _mResponse = MutableLiveData("")
    val mResponse: LiveData<String> = _mResponse

    val _mIsOpenDialog = MutableLiveData(false)
    val mIsOpenDialog: LiveData<Boolean> = _mIsOpenDialog

    private val apiService = ApiService()
    private val localStorage = LocalStorage()

    init {
        _mCityName.value = cityName.value
        _mBackgroundColor.value = currentBackgroundColor.value
        _mWeatherInCity.value = currentWeatherInCity.value
        _mClickWeatherDayInCity.value = clickWeatherDayInCity.value
        _mIsOpenDialog.value = isOpenDialog.value
    }

    fun setClickWeatherDayInCity(clickDayOfWeek: ClickWeatherDayInCityModel) {
        _mClickWeatherDayInCity.value = clickDayOfWeek
    }

    fun setOpenDialog() {
        _mIsOpenDialog.value = !_mIsOpenDialog.value!!
    }

    fun fetchWeatherData(
        context: Context,
    ) {
        apiService.getMainJSONObject(
            context = context,
            nameCity = _mCityName,
            currentWeatherInPlace = _mWeatherInCity,
            currentBackgroundColor = _mBackgroundColor,
            currentResponse = _mResponse,
        )
    }

    fun fetchWeatherData(
        context: Context,
        newCityName: String,
    ) {
        _mCityName.value = newCityName
        apiService.getMainJSONObject(
            context = context,
            nameCity = _mCityName,
            currentWeatherInPlace = _mWeatherInCity,
            currentBackgroundColor = _mBackgroundColor,
            currentResponse = _mResponse,
        )
        localStorage.writeNameCity(checkNotNull(_mCityName.value))
    }
}