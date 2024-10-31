package white.ball.betterweather.domain.model.gson

data class WeatherInCity(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)