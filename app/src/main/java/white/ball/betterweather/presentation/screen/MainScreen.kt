package white.ball.betterweather.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import white.ball.betterweather.R
import white.ball.betterweather.domain.model.PlaceModel
import white.ball.betterweather.ui.theme.MainBlockColor
import java.text.DateFormat
import java.util.Calendar

@Composable
fun MainScreen(currentDay: MutableState<PlaceModel>, clickSync: () -> Unit, clickSearch: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.image_screen_day),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f),
        contentScale = ContentScale.FillBounds)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, end = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
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
                            contentDescription = "Search place",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = currentDay.value.namePlace,
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
                            contentDescription = "Search place",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${
                            currentDay.value.currentTemp.toFloat().toInt()
                        }°",
                        style = TextStyle(
                            fontSize = 60.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )

                    AsyncImage(
                        model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                        contentDescription = "icon_current_weather",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(50.dp)
                    )
                }
                Text(
                    text = currentDay.value.condition,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace
                    )
                )
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
                            text = "${currentDay.value.humidity} %",
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
                            text = "${currentDay.value.feelTemp.toFloat().toInt()}°",
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
                            text = "${currentDay.value.wind} mp/h",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                    }
                }
            }

        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
        ) {
            val currentHour = getCurrentTime().split(":")[0].toInt()

            itemsIndexed(currentDay.value.weatherByHoursList) { index, currentWeatherInHour ->
                val currentHourInRequireLocal = currentWeatherInHour.hours.split(" ")[1].split(":")[0].toInt()
                if (currentHour < currentHourInRequireLocal) {
                    Column(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MainBlockColor)
                            .size(width = 120.dp, height = 110.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "${currentWeatherInHour.hours.split(" ")[1]}",
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
                                .padding(bottom = 5.dp)
                        )
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 5.dp)) {
            itemsIndexed(currentDay.value.weatherByWeekList) { index, currentDay ->
                Row(
                    modifier = Modifier
                        .padding(top = if (index == 0) 0.dp else 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(MainBlockColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                    ) {
                        AsyncImage(
                            model = currentDay.weatherIcon,
                            contentDescription = currentDay.conditionText,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 10.dp))

                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp)
                        ) {
                            Text(
                                text = currentDay.dayOfWeek,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace
                                ),
                                )

                            Text(
                                text = currentDay.shortDateText,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    fontFamily = FontFamily.Monospace
                                ),
                                modifier = Modifier
                                    .padding(top = 2.dp))
                        }
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
                                color = Color.LightGray,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(start = 15.dp, end = 10.dp)
                        )
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
                            ))
                    })
        })
}

private fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time

    return DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime)
}