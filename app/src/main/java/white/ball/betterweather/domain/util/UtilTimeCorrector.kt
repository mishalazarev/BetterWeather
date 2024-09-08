package white.ball.betterweather.domain.util

class UtilTimeCorrector {

    private val pmText = "PM"

    private val maxTimeInt = 24

    fun getCorrectTime(currentTime: String): String {
        val timePart = currentTime.split(" ")[0]
        val hourPart = timePart.split(":")[0].toInt()
        val minutesPart = timePart.split(":")[1]

        val isAPPMText = currentTime.split(" ")[1]

        val correctHour = if (isAPPMText == pmText) {
            val hourPM = hourPart + 12
            if (hourPM < maxTimeInt) {
                "$hourPM"
            } else {
                "0${hourPM - maxTimeInt}"
            }
        } else {
            if (hourPart > 9) {
                "$hourPart"
            } else {
                "0$hourPart"
            }
        }

        return "$correctHour:$minutesPart"
    }

}