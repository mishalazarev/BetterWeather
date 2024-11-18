package white.ball.betterweather.presentation.nav_controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import white.ball.betterweather.domain.model.screen.ScreenNavigation
import white.ball.betterweather.domain.util.InternetUtil
import white.ball.betterweather.presentation.screen.DetailScreen
import white.ball.betterweather.presentation.screen.InternetNotWorking
import white.ball.betterweather.presentation.screen.LoadScreen
import white.ball.betterweather.presentation.screen.MainScreen
import white.ball.betterweather.presentation.view_model.MainScreenViewModel

@Composable
fun MainNavController(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel,
) {
    val internetUtil = InternetUtil()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = ScreenNavigation.LoadScreen.route) {
        composable(route = ScreenNavigation.LoadScreen.route) {
            LoadScreen(
                navigateToMainScreen = {
                    navController.navigate(ScreenNavigation.MainScreen.route)
                },
                navigateToInternetNotWorking = {
                    navController.navigate(ScreenNavigation.InternetNotWorking.route)
                },
            )
        }


        composable(route = ScreenNavigation.InternetNotWorking.route) {
            InternetNotWorking {
                if (internetUtil.isInternetAvailable(context)) {
                        navController.navigate(ScreenNavigation.LoadScreen.route)
                }
            }
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