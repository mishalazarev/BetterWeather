package white.ball.betterweather.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import white.ball.betterweather.R
import white.ball.betterweather.domain.model.Forecastday
import white.ball.betterweather.domain.model.Hour
import white.ball.betterweather.presentation.ui.DialogSearch
import white.ball.betterweather.presentation.ui.component.MoonInfo
import white.ball.betterweather.presentation.ui.component.SunInfo
import white.ball.betterweather.presentation.ui.theme.MainBlockColor
import white.ball.betterweather.presentation.ui.theme.MinTempColor
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import white.ball.betterweather.presentation.view_model.rememberViewModel
import java.text.DateFormat
import java.util.Calendar

@Composable
fun MainScreen(
    clickSync: () -> Unit,
    navigateToDetail: () -> Unit,
    getClickDay: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val mainScreenViewModel = rememberViewModel {
        MainScreenViewModel(
            cityName = it.cityName,
            currentBackgroundColor = it.currentBackgroundColor,
            currentWeatherInCity = it.currentWeatherInCity,
            clickForecastDayModel = it.currentForecastDay,
            isOpenDialog = it.isOpenDialog,
            apiService = it.apiService,
        )
    }

    val currentWeatherInCity = checkNotNull(mainScreenViewModel.mWeatherInCity.value)
    val currentForecastDay = checkNotNull(mainScreenViewModel.mClickWeatherDayInCity.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(checkNotNull(mainScreenViewModel.mBackgroundColor.value))
            .padding(top = 40.dp, end = 5.dp, start = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MainBlockColor,
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        mainScreenViewModel.setOpenDialog()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search city",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = currentWeatherInCity.location.name,
                        style = TextStyle(
                            fontSize = 26.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    IconButton(onClick = {
                        clickSync.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "refresh",
                            tint = Color.White
                        )
                    }
                }
                Text(
                    text = currentWeatherInCity.forecast.forecastday[0].date,
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${
                            currentWeatherInCity.current.temp_c.toFloat().toInt()
                        }°",
                        style = TextStyle(
                            fontSize = 60.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = currentWeatherInCity.current.condition.icon,
                            contentDescription = "icon_current_weather",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(60.dp))
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = currentWeatherInCity.current.condition.text,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_water_drop),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Text(
                            text = "${currentWeatherInCity.current.humidity} %",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                    }
                    Column {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_temperature),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                        )
                        Text(
                            text = "${currentWeatherInCity.current.feelslike_c.toFloat().toInt()}°",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(top = 5.dp, start = 10.dp)
                        )
                    }
                    Column {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_wind),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Text(
                            text = "${currentWeatherInCity.current.wind_mph} mp/h",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp))
                    }
                }
            }

        }

        if (checkNotNull(mainScreenViewModel.mIsOpenDialog.value)) {
            DialogSearch(
                mainScreenViewModel._mIsOpenDialog,
                confirmCity = { cityName ->
                    coroutineScope.launch(Dispatchers.IO) {
                        mainScreenViewModel.fetchWeatherData(cityName)
                    }
                    }
            )
        }

        SunInfo(currentForecastDay.astro.sunrise, currentForecastDay.astro.sunset)

        MoonInfo(currentForecastDay.astro.moonrise, currentForecastDay.astro.moonset)

        WeatherByHoursList(currentWeatherInCity.forecast.forecastday[0].hour)

        WeatherDayOfWeekList(currentWeatherInCity.forecast.forecastday, navigateToDetail, getClickDay)
    }
}

@Composable
private fun WeatherByHoursList(weatherByHoursList: List<Hour>) {
    val currentHour = getCurrentTime().split(":")[0].toInt()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MainBlockColor),
    ) {

        itemsIndexed(weatherByHoursList) { index, currentWeatherInHour ->
            val currentHourInRequireLocal =
                currentWeatherInHour.time.split(" ")[1].split(":")[0].toInt()
            if (currentHour < currentHourInRequireLocal) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .size(width = 120.dp, height = 110.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = currentWeatherInHour.time.split(" ")[1],
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(top = 5.dp)
                        )
                        AsyncImage(
                            model = currentWeatherInHour.condition.icon,
                            contentDescription = currentWeatherInHour.condition.text,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(
                            text = "${currentWeatherInHour.temp_c.toFloat().toInt()}°",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.LightGray,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(bottom = 5.dp, top = 5.dp)
                        )
                    }

                    if (index != weatherByHoursList.size - 1) {
                        VerticalDivider(
                            modifier = Modifier
                                .size(height = 50.dp, width = 1.dp),
                            color = Color.LightGray,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherDayOfWeekList(
    weatherByWeekList: List<Forecastday>,
    navigateToDetail: () -> Unit,
    getClickDay: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        itemsIndexed(weatherByWeekList) { index, _ ->
            val currentDayOfWeek = weatherByWeekList[index]
            Button(onClick = {
                getClickDay(index + 1)
                navigateToDetail()
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (index == 0) 0.dp else 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainBlockColor),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentDayOfWeek.date,
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )

                        Text(
                            text = currentDayOfWeek.date,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(top = 2.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = currentDayOfWeek.day.condition.icon,
                            contentDescription = weatherByWeekList[index].day.condition.text,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 5.dp)
                        )
                        Text(
                            maxLines = 2,
                            modifier = Modifier
                                .width(50.dp),
                            text = currentDayOfWeek.day.condition.text,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                    Row {
                        Text(
                            text = "${currentDayOfWeek.day.maxtemp_c.toFloat().toInt()}°",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )

                        Text(
                            text = "${currentDayOfWeek.day.mintemp_c.toFloat().toInt()}°",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = MinTempColor,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time

    return DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime)
}