package white.ball.betterweather.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import white.ball.betterweather.domain.util.InternetUtil
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import kotlin.random.Random

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoadScreen(
    navigateToMainScreen: () -> Unit,
    navigateToInternetNotWorking: () -> Unit
) {
    val internetUtil = InternetUtil()
    val context = LocalContext.current

    if (internetUtil.isInternetAvailable(context)) {
        navigateToMainScreen()
    } else {
        navigateToInternetNotWorking()
    }
}