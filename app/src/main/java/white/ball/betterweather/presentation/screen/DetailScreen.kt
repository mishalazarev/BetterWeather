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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import white.ball.betterweather.domain.model.ClickWeatherDayInCityModel
import white.ball.betterweather.ui.theme.MainBlockColor
import white.ball.betterweather.ui.theme.MinTempColor

@Composable
fun DetailScreen(
    currentBackgroundColor: MutableState<Brush>,
    clickDay: MutableState<ClickWeatherDayInCityModel>,
    navigate: (Int) -> Unit,
    clickSync: () -> Unit
) {
    val partOfDayText = arrayOf("Night", "Morning", "Afternoon", "Evening")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(currentBackgroundColor.value)
            .padding(top = 40.dp, end = 5.dp, start = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        navigate(0)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "back",
                            tint = Color.White,
                        )
                    }

                    Text(
                        text = clickDay.value.nameCity,
                        style = TextStyle(
                            fontSize = 26.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
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
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = clickDay.value.dayText,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily.SansSerif
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${
                                clickDay.value.maxTemp.toFloat().toInt()
                            }°",
                            style = TextStyle(
                                fontSize = 60.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(top = 15.dp)
                        )
                        Text(
                            text = "${
                                clickDay.value.minTemp.toFloat().toInt()
                            }°",
                            style = TextStyle(
                                fontSize = 40.sp,
                                color = MinTempColor,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .padding(top = 15.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = clickDay.value.iconWeather,
                            contentDescription = "icon_current_weather",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(60.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = clickDay.value.condition,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )
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
                    text = clickDay.value.sunriseTime,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Image(
                    painter = painterResource(R.drawable.icon_sunrise),
                    contentDescription = null
                )
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
                    text = clickDay.value.sunsetTime,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Image(
                    painter = painterResource(R.drawable.icon_sunset),
                    contentDescription = null
                )
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
                    text = clickDay.value.moonrise,
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
                    text = clickDay.value.moonset,
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

        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MainBlockColor)
        ) {
            itemsIndexed(clickDay.value.weatherByHoursList) { index, currentHour ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = partOfDayText[index],
                            modifier = Modifier
                                .size(width = 80.dp, height = 20.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                        VerticalDivider(
                            modifier = Modifier
                                .size(width = 1.dp, height = 30.dp)
                                .padding(start = 10.dp),
                            color = Color.LightGray,
                            thickness = 1.dp
                        )
                    }
                    AsyncImage(
                        model = currentHour.iconWeather,
                        contentDescription = currentHour.conditionText,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = "${currentHour.temp.toFloat().toInt()}°",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.LightGray,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
            }
        }
    }
}