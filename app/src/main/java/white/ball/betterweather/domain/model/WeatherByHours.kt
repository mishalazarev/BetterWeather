package white.ball.betterweather.domain.model

data class WeatherByHours(
    val iconWeather: String,
    val conditionText: String,
    val temp: String,
    val hours: String
)
