package white.ball.betterweather.presentation.view_model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import white.ball.betterweather.presentation.App

class BetterWeatherViewModelFactory(
    private val app: App,
    private val viewModelCreator: (App) -> ViewModel? = { null }
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator(app) as T
    }
}

@Composable
inline fun <reified VM: ViewModel> rememberViewModel(noinline creator: (App) -> VM?): VM {
    val context = LocalContext.current
    val app = context.applicationContext as App
    return viewModel(factory = BetterWeatherViewModelFactory(app, creator))
}