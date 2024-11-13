package white.ball.betterweather.data.api

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import white.ball.betterweather.data.util.APIUtil
import white.ball.betterweather.presentation.TAG
import white.ball.betterweather.domain.model.WeatherInCity

const val API_KEY = "5ab316c38faa4d789b8193014241108"

class APIService {

    fun getMainJSONObject(
        nameCity: String,
        context: Context,
        weatherInCity: MutableLiveData<WeatherInCity>,
        backgroundColor: MutableLiveData<Brush>
    ) {
        val apiUtil = APIUtil()
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                "$API_KEY&q=" +
                nameCity +
                "&days=7&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                weatherInCity.value = apiUtil.convertJSONToWeatherModel(response, backgroundColor)
                Log.d(TAG, "getData: $response")
            },
            {
                Log.d(TAG, "My log Volley Error: $it")
            })
        queue.add(request)
    }
}