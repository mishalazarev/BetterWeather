package white.ball.betterweather.domain.model

import white.ball.betterweather.domain.model.base_model.Astro
import white.ball.betterweather.domain.model.base_model.Day

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)