package white.ball.betterweather.presentation.nav_controller

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import white.ball.betterweather.data.ApiService
import white.ball.betterweather.presentation.TAG
import white.ball.betterweather.presentation.screen.DetailScreen
import white.ball.betterweather.presentation.screen.InternetNotWorking
import white.ball.betterweather.presentation.screen.MainScreen
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import white.ball.betterweather.presentation.view_model.rememberViewModel

@Composable
fun MainNavController(
    navController: NavHostController,
) {
    val apiService = ApiService()
    val mainScreenViewModel = rememberViewModel {
        MainScreenViewModel(
            cityName = it.cityName,
            currentBackgroundColor = it.currentBackgroundColor,
            currentWeatherInCity = it.currentWeatherInCity,
            clickWeatherDayInCityModel = it.clickOtherDayWeather,
            isOpenDialog = it.isOpenDialog
        )
    }
    val context = LocalContext.current

    Log.d(TAG, "MainNavController: ${mainScreenViewModel.mWeatherInCity.value!!.sunsetTime}")

    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = "main_screen") {
            MainScreen(
                clickSync = { mainScreenViewModel.fetchWeatherData(context) },
                navigateToDetail = { navController.navigate("detail_screen") },
                getClickDay = { clickIndexDay ->
                    mainScreenViewModel.setClickWeatherDayInCity(apiService.getClickDay(clickIndexDay, checkNotNull(mainScreenViewModel.mResponse.value)))
                }
            )
        }

        composable(route = "detail_screen") {
            DetailScreen(
                currentBackgroundColor = mainScreenViewModel.mBackgroundColor,
                currentWeatherInPlace = mainScreenViewModel.mClickWeatherDayInCity,
                navigateBack = { navController.popBackStack() },
                clickSync = { mainScreenViewModel.fetchWeatherData(context) }
            )
        }

        composable(route = "internet_not_working") {
            InternetNotWorking(
                loadWeatherFromAPI = { mainScreenViewModel.fetchWeatherData(context) }
            )
        }

    }
}