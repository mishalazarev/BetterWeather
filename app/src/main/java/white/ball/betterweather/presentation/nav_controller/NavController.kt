package white.ball.betterweather.presentation.nav_controller

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import white.ball.betterweather.data.getClickDay
import white.ball.betterweather.data.getData
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.presentation.TAG
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
) {
    NavHost(
        navController = navController,
        startDestination = "main_screen",
    ) {
        composable(
            route = "main_screen",
        ) {
            MainScreen(
                currentWeatherInPlace,
                currentBackgroundColor,
                clickSync = {
                    getData(
                        namePlace = currentWeatherInPlace.value.nameCity,
                        currentResponse = currentResponse,
                        context = context,
                        currentWeatherInPlace = currentWeatherInPlace,
                        currentBackgroundColor = currentBackgroundColor
                    )
                },
                clickSearch = {
                    openDialog.value = true
                },
                {
                    navController.navigate("detail_screen")
                },
                getClickDay = { clickIndexDay ->
                    otherDayWeather.value = getClickDay(clickIndexDay, currentResponse.value)
                })
        }

        composable(
            route = "detail_screen",
        ) {
            DetailScreen(
                currentBackgroundColor = currentBackgroundColor,
                clickDay = otherDayWeather,
                navigate = {
                    navController.navigate("main_screen") {
                        popUpTo("main_screen")
                    }
                },
                clickSync = {
                    getData(
                        namePlace = currentWeatherInPlace.value.nameCity,
                        currentResponse = currentResponse,
                        context = context,
                        currentWeatherInPlace = currentWeatherInPlace,
                        currentBackgroundColor = currentBackgroundColor
                    )
                })
        }
    }
}