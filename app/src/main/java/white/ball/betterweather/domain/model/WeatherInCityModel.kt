package white.ball.betterweather.domain.model

import white.ball.betterweather.domain.model.additional_model.WeatherByHours
import white.ball.betterweather.domain.model.additional_model.WeatherDayOfWeek


data class WeatherInCityModel(
    val nameCity: String,
    val todayDay: String,
    val currentTime: String,
    val currentIconWeather: String,
    val condition: String,
    val currentTemp: String,

    val wind: String,
    val humidity: String,
    val feelTemp: String,

    val sunriseTime: String,
    val sunsetTime: String,

    val moonrise: String,
    val moonset: String,

    val weatherByHoursList: MutableList<WeatherByHours>,
    val weatherByWeekList: MutableList<WeatherDayOfWeek>
)
