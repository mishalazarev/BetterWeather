package white.ball.betterweather.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Brush
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.presentation.TAG
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.domain.model.additional_model.WeatherByHours
import white.ball.betterweather.domain.model.additional_model.WeatherDayOfWeek
import white.ball.betterweather.domain.util.UtilConvertor
import white.ball.betterweather.domain.util.UtilTimeCorrector
import java.text.DateFormat
import java.util.Calendar

const val API_KEY = "5ab316c38faa4d789b8193014241108"

fun getData(
    namePlace: String,
    currentResponse: MutableState<String>,
    context: Context,
    currentWeatherInPlace: MutableState<WeatherInCityModel>,
    currentBackgroundColor: MutableState<Brush>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            "$API_KEY&q=" +
            namePlace +
            "&days=7&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            currentWeatherInPlace.value = getPlaceModel(response, currentBackgroundColor)
            currentResponse.value = response
        },
        {
            Log.d(TAG, "My log Volley Error: $it")
        })
    queue.add(request)
}

fun getClickDay(dayIndex: Int, response: String): ClickWeatherDayInCityModel {
    val utilConvertor = UtilConvertor()
    val utilTimeCorrector = UtilTimeCorrector()

    val mainJSONObject = JSONObject(response)
    val daysJSONArray = mainJSONObject.getJSONObject("forecast").getJSONArray("forecastday")

    val date = utilConvertor.convertDate((daysJSONArray[dayIndex] as JSONObject).getString("date"))
    Log.i(TAG, "getClickDay: ${(daysJSONArray[dayIndex] as JSONObject).getString("date")}\n$dayIndex")

    val conditionJSONObject = (daysJSONArray[dayIndex] as JSONObject).getJSONObject("day").getJSONObject("condition")
    val dayJSONObject = (daysJSONArray[dayIndex] as JSONObject).getJSONObject("day")

    val astroJSONObject = daysJSONArray.getJSONObject(dayIndex).getJSONObject("astro")

    val hoursArray = daysJSONArray.getJSONObject(dayIndex).getJSONArray("hour")

    /** click day weather */

    val nameCity = mainJSONObject.getJSONObject("location").getString("name")

    val iconWeather = "https:${conditionJSONObject.getString("icon")}"
    val condition = conditionJSONObject.getString("text")

    val maxTemp = dayJSONObject.getString("maxtemp_c")
    val minTemp = dayJSONObject.getString("mintemp_c")

    val sunriseTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("sunrise"))
    val sunsetTime =  utilTimeCorrector.getCorrectTime(astroJSONObject.getString("sunset"))

    val moonriseTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("moonrise"))
    val moonsetTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("moonset"))

    /** current hour of day */

    val weatherByHoursList = mutableListOf<WeatherByHours>()
    var hourIndex = 0

    while (hourIndex < 24) {
        val hourObject = hoursArray.getJSONObject(hourIndex)
        val hourResultText = hourObject.getString("time").split(" ")[1]

        weatherByHoursList.add(

            WeatherByHours(
                "https:${
                    hourObject.getJSONObject("condition").getString("icon")
                }",
                hourObject.getJSONObject("condition").getString("text"),
                hourObject.getString("temp_c"),
                hourResultText
            )
        )
        hourIndex += 6
    }

    return ClickWeatherDayInCityModel(
        nameCity,
        date,
        iconWeather,
        condition,
        maxTemp,
        minTemp,

        sunriseTime,
        sunsetTime,

        moonriseTime,
        moonsetTime,

        weatherByHoursList,
    )
}

private fun getPlaceModel(
    response: String,
    currentBackgroundColor: MutableState<Brush>
): WeatherInCityModel {
    val utilConvertor = UtilConvertor()
    val utilTimeCorrector = UtilTimeCorrector()

    val formatDay = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().time)
    var currentDayOfWeekText = formatDay.split(",")[0].replaceFirstChar(Char::titlecase)

    val mainJSONObject = JSONObject(response)

    val currentJSONObject = mainJSONObject.getJSONObject("current")
    val daysJSONArray = mainJSONObject.getJSONObject("forecast").getJSONArray("forecastday")
    val astroJSONObject = daysJSONArray.getJSONObject(0).getJSONObject("astro")

    val dateArray = currentJSONObject.getString("last_updated").split(" ")

    val nameCity = mainJSONObject.getJSONObject("location").getString("name")

    val timeNow = dateArray[1]
    val iconWeather = "https:${currentJSONObject.getJSONObject("condition").getString("icon")}"
    val condition = currentJSONObject.getJSONObject("condition").getString("text")
    val currentTemp = mainJSONObject.getJSONObject("current").getString("temp_c")

    val wind = mainJSONObject.getJSONObject("current").getString("wind_mph")
    val humidity = mainJSONObject.getJSONObject("current").getString("humidity")
    val feelTemp = mainJSONObject.getJSONObject("current").getString("feelslike_c")

    val sunriseTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("sunrise"))
    val sunsetTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("sunset"))

    val moonriseTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("moonrise"))
    val moonsetTime = utilTimeCorrector.getCorrectTime(astroJSONObject.getString("moonset"))

    val hoursArray = daysJSONArray.getJSONObject(0).getJSONArray("hour")

    val weatherByHoursList = mutableListOf<WeatherByHours>()

    for (hour in 0 until 24) {
        val hourObject = hoursArray.getJSONObject(hour)
        val hourResultText = hourObject.getString("time")

        /** weather by hour */

        val linkWeatherIcon = "https:${hourObject.getJSONObject("condition").getString("icon")}"
        val conditionText = hourObject.getJSONObject("condition").getString("text")
        val temp = hourObject.getString("temp_c")

        weatherByHoursList.add(
            WeatherByHours(
                linkWeatherIcon,
                conditionText,
                temp,
                hourResultText
            )
        )

    }

    /** weather in day of week */

    val weatherDayOfWeek = mutableListOf<WeatherDayOfWeek>()

    for (day in 1 until daysJSONArray.length()) {
        currentDayOfWeekText = if (day != 0) {
            utilConvertor.getNextDayOfWeek(currentDayOfWeekText)
        } else {
            utilConvertor.convertDayOfWeekOnEnglish(currentDayOfWeekText)
        }

        val dayJSONnObj = daysJSONArray.getJSONObject(day)
        val dayObject = dayJSONnObj.getJSONObject("day")

        weatherDayOfWeek.add(
            WeatherDayOfWeek(
                utilConvertor.convertDate((daysJSONArray[day] as JSONObject).getString("date")),
                currentDayOfWeekText,
                "https:${dayObject.getJSONObject("condition").getString("icon")}",
                dayObject.getJSONObject("condition").getString("text"),
                dayObject.getString("maxtemp_c"),
                dayObject.getString("mintemp_c")
            )
        )
    }

    /** to change color of background */

    currentBackgroundColor.value = utilConvertor.getBackgroundColorByTime(
        sunriseTime.split(" ")[0].split(":")[0].toInt(),
        sunsetTime.split(" ")[0].split(":")[0].toInt()
    )

    utilConvertor.getTodayDayShort()

    return WeatherInCityModel(
        nameCity,
        utilConvertor.getTodayDayShort(),
        timeNow,
        iconWeather,
        condition,
        currentTemp,

        wind,
        humidity,
        feelTemp,

        sunriseTime,
        sunsetTime,

        moonriseTime,
        moonsetTime,

        weatherByHoursList,
        weatherDayOfWeek
    )
}