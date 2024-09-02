package white.ball.betterweather.domain.util

class UtilConvertor {
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
        val monthArray = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
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
}