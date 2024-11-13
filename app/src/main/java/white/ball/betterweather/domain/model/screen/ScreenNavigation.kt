package white.ball.betterweather.domain.model.screen

sealed class ScreenNavigation(
    val route: String,
) {
    data object LoadScreen : ScreenNavigation("load_screen")
    data object MainScreen : ScreenNavigation("main_screen")
    data object DetailScreen : ScreenNavigation("detail_screen")
}