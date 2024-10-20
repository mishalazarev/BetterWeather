package white.ball.betterweather.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.rememberNavController
import white.ball.betterweather.data.ApiService
import white.ball.betterweather.presentation.ui.theme.BetterWeatherTheme
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.nav_controller.MainNavController
import white.ball.betterweather.presentation.ui.DialogSearch
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    private val apiService = ApiService()

    private val currentWeatherInPlace = mutableStateOf(createDefaultWeatherModel())
    private val clickOtherDayWeather = mutableStateOf(createDefaultClickWeatherModel())
    private val currentResponse = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val openDialog = remember { mutableStateOf(false) }
            val currentBackgroundColor = remember {
                mutableStateOf(Brush.sweepGradient(colors = listOf(Purple80, Pink80)))
            }
            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            BetterWeatherTheme {
                MainNavController(
                    navController,
                    applicationContext,
                    currentWeatherInPlace,
                    currentBackgroundColor,
                    currentResponse,
                    openDialog,
                    clickOtherDayWeather,
                    apiService
                )

                if (openDialog.value) {
                    DialogSearch(
                        openDialog,
                        confirmCity = { cityName -> handleCitySearch(cityName, currentBackgroundColor) }
                    )
                }

                fetchWeatherData(currentWeatherInPlace.value.nameCity, currentBackgroundColor)
            }
        }
    }

    private fun handleCitySearch(cityName: String, currentBackgroundColor: MutableState<Brush>) {
        fetchWeatherData(cityName, currentBackgroundColor)
    }

    private fun fetchWeatherData(cityName: String, currentBackgroundColor: MutableState<Brush>) {
        apiService.getMainJSONObject(
            namePlace = cityName,
            currentResponse = currentResponse,
            context = this,
            currentWeatherInPlace = currentWeatherInPlace,
            currentBackgroundColor = currentBackgroundColor
        )
    }

    private companion object {
        fun createDefaultWeatherModel() = WeatherInCityModel(
            "Rostov-Na-Donu", "", "00:00", "", "",
            "0", "0", "0", "0",
            "00:00", "00:00",
            "00:00", "00:00",
            mutableListOf(),
            mutableListOf()
        )

        fun createDefaultClickWeatherModel() = ClickWeatherDayInCityModel(
            "", "", "", "",
            "0", "0",
            "00:00", "00:00",
            "00:00", "00:00",
            mutableListOf()
        )
    }
}