package white.ball.betterweather.domain.model

import white.ball.betterweather.domain.model.additional_model.WeatherByHours

data class ClickWeatherDayInCityModel(
    val nameCity: String,
    val dayText: String,

    val iconWeather: String,
    val condition: String,

    val maxTemp: String,
    val minTemp: String,

    val sunriseTime: String,
    val sunsetTime: String,

    val moonrise: String,
    val moonset: String,

    val weatherByHoursList: MutableList<WeatherByHours>,
)