package white.ball.betterweather.presentation.nav_controller

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import white.ball.betterweather.data.ApiService
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.screen.DetailScreen
import white.ball.betterweather.presentation.screen.MainScreen

@Composable
fun MainNavController(
    navController: NavHostController,
    context: Context,
    currentWeatherInPlace: MutableState<WeatherInCityModel>,
    currentBackgroundColor: MutableState<Brush>,
    currentResponse: MutableState<String>,
    openDialog: MutableState<Boolean>,
    otherDayWeather: MutableState<ClickWeatherDayInCityModel>,
    apiService: ApiService
) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = "main_screen") {
            MainScreen(
                currentWeatherInPlace = currentWeatherInPlace,
                currentBackgroundColor = currentBackgroundColor,
                clickSync = { fetchWeatherData(currentWeatherInPlace.value.nameCity, context, apiService, currentResponse, currentWeatherInPlace, currentBackgroundColor) },
                clickSearch = { openDialog.value = true },
                navigateToDetail = { navController.navigate("detail_screen") },
                getClickDay = { clickIndexDay -> otherDayWeather.value = apiService.getClickDay(clickIndexDay, currentResponse.value) }
            )
        }

        composable(route = "detail_screen") {
            DetailScreen(
                currentBackgroundColor = currentBackgroundColor,
                currentWeatherInPlace = otherDayWeather,
                navigateBack = { navController.popBackStack() },
                clickSync = { fetchWeatherData(currentWeatherInPlace.value.nameCity, context, apiService, currentResponse, currentWeatherInPlace, currentBackgroundColor) }
            )
        }
    }
}

private fun fetchWeatherData(
    cityName: String,
    context: Context,
    apiService: ApiService,
    currentResponse: MutableState<String>,
    currentWeatherInPlace: MutableState<WeatherInCityModel>,
    currentBackgroundColor: MutableState<Brush>
) {
    apiService.getMainJSONObject(
        namePlace = cityName,
        currentResponse = currentResponse,
        context = context,
        currentWeatherInPlace = currentWeatherInPlace,
        currentBackgroundColor = currentBackgroundColor
    )
}