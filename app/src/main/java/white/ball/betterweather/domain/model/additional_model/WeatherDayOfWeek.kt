package white.ball.betterweather.domain.model.additional_model

data class WeatherDayOfWeek(
    val shortDateText: String,
    val dayOfWeek: String,
    val weatherIcon: String,
    val conditionText: String,

    val tempMax: String,
    val tempMin: String,
)
