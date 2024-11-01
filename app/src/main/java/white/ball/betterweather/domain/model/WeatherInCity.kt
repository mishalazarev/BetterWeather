package white.ball.betterweather.domain.model

import white.ball.betterweather.domain.model.base_model.Location

data class WeatherInCity(
    val current: Current = Current(),
    val forecast: Forecast = Forecast(),
    val location: Location = Location()
)