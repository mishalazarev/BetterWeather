package white.ball.betterweather.data.util

import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import white.ball.betterweather.domain.model.ClickedWeatherInCity
import white.ball.betterweather.domain.model.WeatherInCity
import white.ball.betterweather.domain.model.additional_model.WeatherByHours
import white.ball.betterweather.domain.util.ConvertorUtil
import white.ball.betterweather.domain.util.TimeCorrectorUtil
import java.text.DateFormat
import java.util.Calendar

class APIUtil {

    fun convertJSONToWeatherModel(
        response: String,
        currentBackgroundColor: MutableLiveData<Brush>
    ): WeatherInCity {
        val convertorUtil = ConvertorUtil()
        val timeCorrectorUtil = TimeCorrectorUtil()

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

        val sunriseTime = timeCorrectorUtil.getCorrectTime(astroJSONObject.getString("sunrise"))
        val sunsetTime = timeCorrectorUtil.getCorrectTime(astroJSONObject.getString("sunset"))

        /** weather in day of week */

        val weatherDayOfWeek = mutableListOf<ClickedWeatherInCity>()

        for (day in 0 until daysJSONArray.length()) {
            currentDayOfWeekText = if (day != 0) {
                convertorUtil.getNextDayOfWeek(currentDayOfWeekText)
            } else {
                convertorUtil.convertDayOfWeekOnEnglish(currentDayOfWeekText)
            }

            val dayJSONnObj = daysJSONArray.getJSONObject(day)
            val dayObject = dayJSONnObj.getJSONObject("day")
            val astroObject = daysJSONArray.getJSONObject(day).getJSONObject("astro")


            /** weather by hour */

            val hoursArray = daysJSONArray.getJSONObject(day).getJSONArray("hour")

            val weatherByHoursList = mutableListOf<WeatherByHours>()

            val countHours = if (day != 0) {
                4
            } else {
                24
            }

            var indexHour = 0

            for (hour in 0 until countHours) {

                val hourObject = hoursArray.getJSONObject(indexHour)
                val hourResultText = hourObject.getString("time")

                val linkWeatherIcon = "https:${hourObject.getJSONObject("condition").getString("icon")}"
                val conditionText = hourObject.getJSONObject("condition").getString("text")
                val temp = hourObject.getString("temp_c")

                when (countHours) {
                    4 -> {
                        indexHour += 6
                    }
                    24 -> {
                        indexHour++
                    }
                    else -> throw IllegalArgumentException("don't have for information about hours in this day [$countHours]")
                }

                weatherByHoursList.add(
                    WeatherByHours(
                        linkWeatherIcon,
                        conditionText,
                        temp,
                        hourResultText
                    )
                )
            }


            weatherDayOfWeek.add(
                ClickedWeatherInCity(
                    convertorUtil.convertDate((daysJSONArray[day] as JSONObject).getString("date")),
                    currentDayOfWeekText,
                    "https:${dayObject.getJSONObject("condition").getString("icon")}",
                    dayObject.getJSONObject("condition").getString("text"),
                    dayObject.getString("maxtemp_c"),
                    dayObject.getString("mintemp_c"),

                    timeCorrectorUtil.getCorrectTime(astroObject.getString("sunrise")),
                    timeCorrectorUtil.getCorrectTime(astroObject.getString("sunset")),

                    timeCorrectorUtil.getCorrectTime(astroObject.getString("moonrise")),
                    timeCorrectorUtil.getCorrectTime(astroObject.getString("moonset")),

                    weatherByHoursList
                )
            )
        }

        /** to change color of background */

        currentBackgroundColor.value = convertorUtil.getBackgroundColorByTime(
            sunriseTime.split(" ")[0].split(":")[0].toInt(),
            sunsetTime.split(" ")[0].split(":")[0].toInt()
        )

        convertorUtil.getTodayDayShort()

        return WeatherInCity(
            nameCity,
            timeNow,
            iconWeather,
            condition,
            currentTemp,

            wind,
            humidity,
            feelTemp,

            weatherDayOfWeek
        )
    }

}