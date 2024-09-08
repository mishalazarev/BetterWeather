package white.ball.betterweather.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.rememberNavController
import white.ball.betterweather.ui.theme.BetterWeatherTheme
import white.ball.betterweather.data.getData
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.nav_controller.MainNavController
import white.ball.betterweather.presentation.screen.DialogSearch
import white.ball.betterweather.ui.theme.Pink80
import white.ball.betterweather.ui.theme.Purple80

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    private val currentWeatherInPlace = mutableStateOf(
        WeatherInCityModel(
            "Rostov-Na-Donu",
            "",
            "00:00",
            "",
            "",
            "0",
            "0",
            "0",
            "0",

            "00:00",
            "00:00",

            "00:00",
            "00:00",

            mutableListOf(),
            mutableListOf()
        )
    )

    private val clickOtherDayWeather = mutableStateOf(
        ClickWeatherDayInCityModel(
            "",
            "",
            "",
            "",
            "0",
            "0",
            "00:00",
            "00:00",

            "00:00",
            "00:00",

            mutableListOf(),
        )
    )

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
                    clickOtherDayWeather)

                if (openDialog.value) {
                    DialogSearch(
                        openDialog,
                        confirmCity = {
                            getData(
                                namePlace = it,
                                currentResponse = currentResponse,
                                context = this@MainActivity,
                                currentWeatherInPlace = currentWeatherInPlace,
                                currentBackgroundColor = currentBackgroundColor
                            )
                        })
                }

                getData(
                    namePlace = currentWeatherInPlace.value.nameCity,
                    currentResponse = currentResponse,
                    context = this,
                    currentWeatherInPlace = currentWeatherInPlace,
                    currentBackgroundColor = currentBackgroundColor
                )
            }
        }
    }
}

