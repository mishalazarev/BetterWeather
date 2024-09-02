package white.ball.betterweather.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import white.ball.betterweather.presentation.TAG
import white.ball.betterweather.domain.model.PlaceModel
import white.ball.betterweather.domain.model.WeatherByHours
import white.ball.betterweather.domain.model.WeatherDayOfWeek
import white.ball.betterweather.domain.util.UtilConvertor
import java.text.DateFormat
import java.util.Calendar

const val API_KEY = "5ab316c38faa4d789b8193014241108"
fun getData(namePlace: String, context: Context, currentWeatherInPlace: MutableState<PlaceModel>) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            "$API_KEY&q=" +
            "$namePlace" +
            "&days=7&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val request = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
                currentWeatherInPlace.value = getPlaceModel(response)
        },
        {
            Log.d(TAG, "My log Volley Error: $it")
        })
    queue.add(request)
}

private fun getPlaceModel(response: String): PlaceModel {
    val utilConvertor = UtilConvertor()

    val formatDay = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().time)
    var currentDayOfWeekText = formatDay.split(",")[0].replaceFirstChar(Char::titlecase)

    val mainJSONObject = JSONObject(response)

    val currentJSONObject = mainJSONObject.getJSONObject("current")
    val daysJSONArray = mainJSONObject.getJSONObject("forecast").getJSONArray("forecastday")

    val dateArray = currentJSONObject.getString("last_updated").split(" ")

    val namePlace = mainJSONObject.getJSONObject("location").getString("name")
    val dateToday = dateArray[0]
    val timeNow = dateArray[1]
    val iconWeather = currentJSONObject.getJSONObject("condition").getString("icon")
    val condition = currentJSONObject.getJSONObject("condition").getString("text")
    val currentTemp = mainJSONObject.getJSONObject("current").getString("temp_c")
    val wind = mainJSONObject.getJSONObject("current").getString("wind_mph")
    val humidity = mainJSONObject.getJSONObject("current").getString("humidity")
    val feelTemp = mainJSONObject.getJSONObject("current").getString("feelslike_c")

    val hoursArray = (daysJSONArray[0] as JSONObject).getJSONArray("hour")

    /*-- temp by hour */

    val weatherByHoursList = mutableListOf<WeatherByHours>()

    for (hour in 0 until 24) {
        val hourObject = hoursArray.getJSONObject(hour)
        weatherByHoursList.add(
            WeatherByHours(
                "https:${
                    hourObject.getJSONObject("condition").getString("icon")
                }",
                hourObject.getJSONObject("condition").getString("text"),
                hourObject.getString("temp_c"),
                hourObject.getString("time")
            )
        )
    }

    /*-- weather in day of week */

    val weatherDayOfWeek = mutableListOf<WeatherDayOfWeek>()

    for (day in 0 until daysJSONArray.length()) {
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
                "https:${(daysJSONArray[day] as JSONObject).getJSONObject("day").getJSONObject("condition").getString("icon")}",
                dayObject.getJSONObject("condition").getString("text"),
                dayObject.getString("maxtemp_c"),
                dayObject.getString("mintemp_c")
            )
        )
    }

    return PlaceModel(
        namePlace,
        dateToday,
        timeNow,
        iconWeather,
        condition,
        currentTemp,
        wind,
        humidity,
        feelTemp,
        weatherByHoursList,
        weatherDayOfWeek
    )
}