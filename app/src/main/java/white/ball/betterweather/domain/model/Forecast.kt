package white.ball.betterweather.domain.model

data class Forecast(
    val forecastday: List<Forecastday> = mutableListOf()
)