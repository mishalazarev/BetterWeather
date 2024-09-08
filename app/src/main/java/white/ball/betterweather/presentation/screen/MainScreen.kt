package white.ball.betterweather.presentation.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import white.ball.betterweather.domain.model.WeatherInCityModel
import white.ball.betterweather.ui.theme.MainBlockColor
import white.ball.betterweather.ui.theme.MinTempColor
import java.text.DateFormat
import java.util.Calendar

@Composable
fun MainScreen(
    weatherInCity: MutableState<WeatherInCityModel>,
    currentBackgroundColor: MutableState<Brush>,
    clickSync: () -> Unit,
    clickSearch: () -> Unit,
    navigate: () -> Unit,
    getClickDay: (Int) -> Unit
) {
    val currentHour = getCurrentTime().split(":")[0].toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(currentBackgroundColor.value)
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
                        clickSearch.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search city",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = weatherInCity.value.nameCity,
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
                    text = weatherInCity.value.todayDay,
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
                            weatherInCity.value.currentTemp.toFloat().toInt()
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
                            model = weatherInCity.value.currentIconWeather,
                            contentDescription = "icon_current_weather",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(60.dp))
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = weatherInCity.value.condition,
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
                            text = "${weatherInCity.value.humidity} %",
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
                            text = "${weatherInCity.value.feelTemp.toFloat().toInt()}°",
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
                            text = "${weatherInCity.value.wind} mp/h",
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MainBlockColor),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherInCity.value.sunriseTime,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp))
                Image(
                    painter = painterResource(R.drawable.icon_sunrise),
                    contentDescription = null)
                Text(
                    text = "Sunrise",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(bottom = 5.dp, top = 5.dp)
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .size(height = 70.dp, width = 2.dp),
                color = Color.LightGray,
                thickness = 2.dp,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherInCity.value.sunsetTime,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp))
                Image(
                    painter = painterResource(R.drawable.icon_sunset),
                    contentDescription = null)
                Text(
                    text = "Sunset",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(bottom = 5.dp, top = 5.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MainBlockColor),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherInCity.value.moonrise,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp))
                Image(
                    painter = painterResource(R.drawable.icon_moonrise),
                    contentDescription = null)
                Text(
                    text = "Moonrise",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(bottom = 5.dp, top = 5.dp)
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .size(height = 70.dp, width = 2.dp),
                color = Color.LightGray,
                thickness = 2.dp,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherInCity.value.moonset,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp))
                Image(
                    painter = painterResource(R.drawable.icon_moonset),
                    contentDescription = null)
                Text(
                    text = "Moonset",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(bottom = 5.dp, top = 5.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MainBlockColor),
        ) {

            itemsIndexed(weatherInCity.value.weatherByHoursList) { index, currentWeatherInHour ->
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

                        if (index != weatherInCity.value.weatherByHoursList.size - 1) {
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            itemsIndexed(weatherInCity.value.weatherByWeekList) { index, currentDay ->
                Button(onClick = {
                    getClickDay(index + 1)
                    navigate()
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
                                    text = currentDay.shortDateText,
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
                                    model = currentDay.weatherIcon,
                                    contentDescription = currentDay.conditionText,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(start = 5.dp)
                                )
                                Text(
                                    maxLines = 2,
                                    modifier = Modifier
                                        .width(50.dp),
                                    text = currentDay.conditionText,
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
                                text = "${currentDay.tempMax.toFloat().toInt()}°",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace
                                )
                            )

                            Text(
                                text = "${currentDay.tempMin.toFloat().toInt()}°",
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
}


@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, confirmCity: (String) -> Unit) {
    val dialogText = remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                dialogState.value = false
                confirmCity(dialogText.value)
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = {
                dialogState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            OutlinedTextField(
                value = dialogText.value, onValueChange = {
                    dialogText.value = it
                },
                label = {
                    Text(
                        text = "Enter the city name",
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                })
        })
}

private fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time

    return DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime)
}