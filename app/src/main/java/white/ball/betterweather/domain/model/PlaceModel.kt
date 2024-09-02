package white.ball.betterweather.domain.model

data class PlaceModel(
    val namePlace: String,
    val todayDayString: String,
    val currentTime: String,
    val currentIconWeather: String,
    val condition: String,
    val currentTemp: String,

    val wind: String,
    val humidity: String,
    val feelTemp: String,

    val weatherByHoursList: MutableList<WeatherByHours>,
    val weatherByWeekList: MutableList<WeatherDayOfWeek>
)
