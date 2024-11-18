package white.ball.betterweather.presentation

import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import white.ball.betterweather.presentation.ui.theme.BetterWeatherTheme
import white.ball.betterweather.domain.model.ClickedWeatherInCity
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.presentation.nav_controller.MainNavController
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import white.ball.betterweather.presentation.view_model.MainViewModelFactory

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainScreenViewModel = viewModel(factory = MainViewModelFactory(applicationContext as Application))

            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            mainViewModel.initInformation()

            BetterWeatherTheme {
                MainNavController(
                    navController,
                    mainViewModel,
                )

            }
        }
    }

    companion object {
        fun createWeatherInCity() = WeatherInCity(
            "Rostov-Na-Donu",  "00:00", "", "",
            "0", "0", "0", "0",
            mutableListOf(createClickedWeatherInCity())
        )

        fun createClickedWeatherInCity() = ClickedWeatherInCity(
            "", "", "", "",
            "0", "0",
            "00:00", "00:00",
            "00:00", "00:00",
            mutableListOf()
        )
    }
}