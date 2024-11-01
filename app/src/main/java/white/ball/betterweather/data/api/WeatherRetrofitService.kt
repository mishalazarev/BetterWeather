package white.ball.betterweather.data.api

import retrofit2.http.GET
import white.ball.betterweather.domain.model.WeatherInCity
import retrofit2.http.Query

interface WeatherRetrofitService {

    @GET("v1/forecast.json")
    suspend fun getWeatherInCity(
        @Query("key") apiKey: String = "5ab316c38faa4d789b8193014241108",
        @Query("q") nameCity: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherInCity

}