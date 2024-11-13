package white.ball.betterweather.domain.model

import white.ball.betterweather.domain.model.additional_model.WeatherByHours

data class ClickedWeatherInCity(
    val todayDay: String,
    val dayOfWeek: String,

    val iconWeather: String,
    val condition: String,

    val maxTemp: String,
    val minTemp: String,

    val sunrise: String,
    val sunset: String,

    val moonrise: String,
    val moonset: String,

    val weatherByHoursList: MutableList<WeatherByHours>,
)