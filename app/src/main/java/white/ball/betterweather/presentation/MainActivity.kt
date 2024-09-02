package white.ball.betterweather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import white.ball.betterweather.ui.theme.BetterWeatherTheme
import white.ball.betterweather.data.getData
import white.ball.betterweather.domain.model.PlaceModel
import white.ball.betterweather.presentation.screen.DialogSearch
import white.ball.betterweather.presentation.screen.MainScreen

const val TAG = "tag"

class MainActivity : ComponentActivity() {

    private val currentWeatherInPlace = mutableStateOf(
        PlaceModel(
            "London",
            "",
            "00:00",
            "",
            "",
            "0.0",
            "0",
            "0",
            "0",
            mutableListOf(),
            mutableListOf()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BetterWeatherTheme {
                val openDialog = remember { mutableStateOf(false) }

                if (openDialog.value) {
                    DialogSearch(openDialog,
                        confirmCity = {
                            getData(
                                namePlace = it,
                                context = this,
                                currentWeatherInPlace = currentWeatherInPlace
                            )
                        })
                }

                MainScreen(
                    currentWeatherInPlace,
                    clickSync = {
                        getData(
                            namePlace = currentWeatherInPlace.value.namePlace,
                            context = this,
                            currentWeatherInPlace = currentWeatherInPlace
                        )
                    },
                    clickSearch = {
                        openDialog.value = true
                    })

                getData(
                    namePlace = currentWeatherInPlace.value.namePlace,
                    context = this,
                    currentWeatherInPlace = currentWeatherInPlace
                )
            }
        }
    }
}

