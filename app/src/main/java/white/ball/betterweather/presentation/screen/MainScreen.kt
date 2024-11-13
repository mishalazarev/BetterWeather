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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import white.ball.betterweather.R
import white.ball.betterweather.domain.model.ClickedWeatherInCity
import white.ball.betterweather.domain.model.additional_model.WeatherByHours
import white.ball.betterweather.presentation.MainActivity.Companion.createClickedWeatherInCity
import white.ball.betterweather.presentation.MainActivity.Companion.createWeatherInCity
import white.ball.betterweather.presentation.ui.DialogSearch
import white.ball.betterweather.presentation.ui.component.MoonInfo
import white.ball.betterweather.presentation.ui.component.SunInfo
import white.ball.betterweather.presentation.ui.theme.MainBlockColor
import white.ball.betterweather.presentation.ui.theme.MinTempColor
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80
import white.ball.betterweather.presentation.view_model.MainScreenViewModel
import java.text.DateFormat
import java.util.Calendar

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navigateToDetail: () -> Unit,
    getClickDay: (Int) -> Unit
) {
    val weatherInCity = viewModel.weatherInCity.observeAsState(createWeatherInCity()).value
    val todayWeatherInCity = viewModel.clickedWeatherInCity.observeAsState(createClickedWeatherInCity()).value
    val backgroundColor = viewModel.backgroundColor.observeAsState(Brush.sweepGradient(
        colors = listOf(Purple80, Pink80)
    )).value
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
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
                        openDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search city",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = weatherInCity.nameCity,
                        style = TextStyle(
                            fontSize = 26.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "refresh",
                            tint = Color.White
                        )
                    }
                }
                Text(
                    text = todayWeatherInCity.todayDay,
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
                            weatherInCity.currentTemp.toFloat().toInt()
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
                            model = weatherInCity.currentIconWeather,
                            contentDescription = "icon_current_weather",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(60.dp))
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = weatherInCity.condition,
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
                            text = "${weatherInCity.humidity} %",
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
                            text = "${weatherInCity.feelTemp.toFloat().toInt()}°",
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
                            text = "${weatherInCity.wind} mp/h",
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

        SunInfo(todayWeatherInCity.sunrise, todayWeatherInCity.sunset)

        MoonInfo(todayWeatherInCity.moonrise, todayWeatherInCity.moonset)

        WeatherByHoursList(todayWeatherInCity.weatherByHoursList)

        WeatherDayOfWeekList(weatherInCity.weatherByWeekList, navigateToDetail, getClickDay)
    }

    if (openDialog.value) {
        DialogSearch(
            openDialog,
            confirmCity = { cityName ->
                viewModel.changeCity(cityName)
            }
        )
    }
}

@Composable
private fun WeatherByHoursList(weatherByHoursList: List<WeatherByHours>) {
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
                currentWeatherInHour.hours.split(" ")[1].split(":")[0].toInt()
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
                            text = currentWeatherInHour.hours.split(" ")[1],
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(top = 5.dp)
                        )
                        AsyncImage(
                            model = currentWeatherInHour.iconWeather,
                            contentDescription = currentWeatherInHour.conditionText,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(
                            text = "${currentWeatherInHour.temp.toFloat().toInt()}°",
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
    weatherByWeekList: MutableList<ClickedWeatherInCity>,
    navigateToDetail: () -> Unit,
    getClickDay: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        itemsIndexed(weatherByWeekList) { index, currentDay ->
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
                            text = currentDay.dayOfWeek,
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )

                        Text(
                            text = currentDay.todayDay,
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
                            model = currentDay.iconWeather,
                            contentDescription = currentDay.condition,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 5.dp)
                        )
                        Text(
                            maxLines = 2,
                            modifier = Modifier
                                .width(50.dp),
                            text = currentDay.condition,
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
                            text = "${currentDay.maxTemp.toFloat().toInt()}°",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )

                        Text(
                            text = "${currentDay.minTemp.toFloat().toInt()}°",
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