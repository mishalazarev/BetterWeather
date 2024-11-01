package white.ball.betterweather.presentation.nav_controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import white.ball.betterweather.presentation.screen.DetailScreen
import white.ball.betterweather.presentation.screen.InternetNotWorking
import white.ball.betterweather.presentation.screen.MainScreen
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import white.ball.betterweather.presentation.view_model.rememberViewModel

@Composable
fun MainNavController(
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
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

//    Log.d(TAG, "MainNavController: ${mainScreenViewModel.mWeatherInCity.value!!.forecast.forecastday[0].astro.sunset}")

    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = "main_screen") {
            MainScreen(
                clickSync = { coroutineScope.launch (Dispatchers.IO) {
                    mainScreenViewModel.fetchWeatherData()
                } },
                navigateToDetail = { navController.navigate("detail_screen") },
                getClickDay = { clickIndexDay ->
                    mainScreenViewModel.setClickWeatherDayInCity(clickIndexDay)
                }
            )
        }

        composable(route = "detail_screen") {
            DetailScreen(
                currentBackgroundColor = mainScreenViewModel.mBackgroundColor,
                weatherInCity = mainScreenViewModel.mWeatherInCity,
                forecastDay = mainScreenViewModel.mClickWeatherDayInCity,
                navigateBack = { navController.popBackStack() },
                clickSync = { coroutineScope.launch (Dispatchers.IO) {
                    mainScreenViewModel.fetchWeatherData()
                } }
            )
        }

        composable(route = "internet_not_working") {
            InternetNotWorking(
                loadWeatherFromAPI = { coroutineScope.launch(Dispatchers.IO) {
                    mainScreenViewModel.fetchWeatherData()
                } }
            )
        }

    }
}