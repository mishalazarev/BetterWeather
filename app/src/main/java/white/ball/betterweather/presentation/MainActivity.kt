package white.ball.betterweather.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import white.ball.betterweather.presentation.ui.theme.BetterWeatherTheme
import white.ball.betterweather.presentation.nav_controller.MainNavController
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import white.ball.betterweather.presentation.view_model.rememberViewModel

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            val mainScreenViewModel = rememberViewModel {
                MainScreenViewModel(
                    cityName = it.cityName,
                    currentBackgroundColor = it.currentBackgroundColor,
                    currentWeatherInCity = it.currentWeatherInCity,
                    clickForecastDayModel = it.currentForecastDay,
                    isOpenDialog = it.isOpenDialog,
                    apiService = it.apiService,
                )
            }

            coroutineScope.launch(Dispatchers.IO) {
                mainScreenViewModel.fetchWeatherData()
            }
            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            BetterWeatherTheme {
                MainNavController(
                    navController,
                )
            }
        }
    }
}