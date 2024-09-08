package white.ball.betterweather.domain.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Brush
import white.ball.betterweather.presentation.TAG
import white.ball.betterweather.ui.theme.DayEndColor
import white.ball.betterweather.ui.theme.DayStartColor
import white.ball.betterweather.ui.theme.NightEndColor
import white.ball.betterweather.ui.theme.NightStartColor
import java.text.DateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

class UtilConvertor {

    private val calendar = Calendar.getInstance()

    private val monthArray = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")

    fun getNextDayOfWeek(dayOfWeek: String): String {
        when(dayOfWeek) {
            "Понедельник", "Monday" -> return "Tuesday"
            "Вторник", "Tuesday" -> return "Wednesday"
            "Среда", "Wednesday" -> return "Thursday"
            "Четверг", "Thursday" -> return "Friday"
            "Пятница", "Friday" -> return "Saturday"
            "Суббота", "Saturday" -> return "Sunday"
            "Воскресенье", "Sunday" -> return "Monday"
        }

        return throw IllegalArgumentException("Unknown day of week")
    }

    fun convertDate(date: String): String {
        val currentMonth = date.split("-")[1]
        val day = date.split("-")[2]

        return "$day ${monthArray[currentMonth.toInt() + 1]}"
    }

    fun convertDayOfWeekOnEnglish(dayOfWeek: String): String {
        val weekEnglishArray = arrayOf("Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
        val weekRussianArray = arrayOf("Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье")

        for (index in weekRussianArray.indices) {
            if (weekRussianArray[index] == dayOfWeek) return weekEnglishArray[index]
        }
        return throw IllegalArgumentException("Unknown day of week")
    }

    fun getBackgroundColorByTime(sunriseTime: Int, sunsetTime: Int): Brush {
        val timeResult = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)

        val currentHour = timeResult.split(":")[0].toInt()

        val mainBackgroundColorByTime = arrayOf(
            Brush.radialGradient(
                colors = listOf(NightEndColor, NightStartColor),
                radius = 300f),

            Brush.radialGradient(
                colors = listOf(DayEndColor, DayStartColor),
                radius = 400f))

        return if (currentHour in sunriseTime..sunsetTime) {
            mainBackgroundColorByTime[1]
        } else {
            mainBackgroundColorByTime[0]
        }
    }

    fun getTodayDayShort(): String {
        val englishDayOfWeekText = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time).split(",")[0].replaceFirstChar { it.titlecase() }
        return "${calendar.get(Calendar.DATE)} ${monthArray[calendar.get(Calendar.MONTH)]}, " +
                convertDayOfWeekOnEnglish(englishDayOfWeekText)
    }


}