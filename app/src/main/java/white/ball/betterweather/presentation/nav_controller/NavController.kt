package white.ball.betterweather.presentation.nav_controller

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import white.ball.betterweather.data.api.APIService
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.domain.model.screen.ScreenNavigation
import white.ball.betterweather.presentation.screen.DetailScreen
import white.ball.betterweather.presentation.screen.LoadScreen
import white.ball.betterweather.presentation.screen.MainScreen
import white.ball.betterweather.presentation.view_model.MainScreenViewModel

@Composable
fun MainNavController(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel,
) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = ScreenNavigation.LoadScreen.route) {
            LoadScreen(
//                viewModel = mainScreenViewModel,
//                navigateToMainScreen = {  },
//                navigateTo
            )
        }

        composable(route = ScreenNavigation.MainScreen.route) {
            MainScreen(
                viewModel = mainScreenViewModel,
                navigateToDetail = { navController.navigate("detail_screen") },
                getClickDay = { clickIndexDay -> mainScreenViewModel.setClickedWeatherInCity(clickIndexDay)}
            )
        }

        composable(route = ScreenNavigation.DetailScreen.route) {
            DetailScreen(
                viewModel = mainScreenViewModel,
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}