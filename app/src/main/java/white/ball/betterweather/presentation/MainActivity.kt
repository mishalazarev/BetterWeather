package white.ball.betterweather.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import white.ball.betterweather.presentation.ui.theme.BetterWeatherTheme
import white.ball.betterweather.presentation.nav_controller.MainNavController

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            BetterWeatherTheme {
                MainNavController(
                    navController,
                )
            }
        }
    }



}