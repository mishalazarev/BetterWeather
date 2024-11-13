package white.ball.betterweather.presentation.screen

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import white.ball.betterweather.domain.model.ClickedWeatherInCity
import white.ball.betterweather.domain.model.additional_model.WeatherByHours
import white.ball.betterweather.presentation.MainActivity.Companion.createClickedWeatherInCity
import white.ball.betterweather.presentation.MainActivity.Companion.createWeatherInCity
import white.ball.betterweather.presentation.ui.component.MoonInfo
import white.ball.betterweather.presentation.ui.component.SunInfo
import white.ball.betterweather.presentation.ui.theme.MainBlockColor
import white.ball.betterweather.presentation.ui.theme.MinTempColor
import white.ball.betterweather.presentation.ui.theme.Pink80
import white.ball.betterweather.presentation.ui.theme.Purple80
import white.ball.betterweather.presentation.view_model.MainScreenViewModel

@Composable
fun DetailScreen(
    viewModel: MainScreenViewModel,
    navigateBack: () -> Unit,
) {
    val weatherInCity = viewModel.weatherInCity.observeAsState(createWeatherInCity()).value
    val clickedWeatherInCity = viewModel.clickedWeatherInCity.observeAsState(createClickedWeatherInCity()).value
    val backgroundColor = viewModel.backgroundColor.observeAsState(Brush.sweepGradient(
        colors = listOf(Purple80, Pink80)
    )).value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(end = 5.dp, start = 5.dp, top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainCardInfo(clickedWeatherInCity, weatherInCity.nameCity, navigateBack)

        MoonInfo(clickedWeatherInCity.moonrise, clickedWeatherInCity.moonset)

        SunInfo(clickedWeatherInCity.sunrise, clickedWeatherInCity.sunset)

        WeatherByHourPartOfDayList(clickedWeatherInCity.weatherByHoursList)

    }
}


@Composable
private fun MainCardInfo(
    clickedWeatherInPlace: ClickedWeatherInCity,
    nameCity: String,
    navigateBack: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MainBlockColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = "back",
                        tint = Color.White,
                    )
                }
                Text(
                    text = nameCity,
                    style = TextStyle(
                        fontSize = 26.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )
                )
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "refresh",
                        tint = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = clickedWeatherInPlace.dayOfWeek,
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${clickedWeatherInPlace.maxTemp.toFloat().toInt()}°",
                        style = TextStyle(
                            fontSize = 60.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = "${clickedWeatherInPlace.minTemp.toFloat().toInt()}°",
                        style = TextStyle(
                            fontSize = 40.sp,
                            color = MinTempColor,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = clickedWeatherInPlace.iconWeather,
                        contentDescription = "icon_current_weather",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(60.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = clickedWeatherInPlace.condition,
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
}

@Composable
private fun WeatherByHourPartOfDayList(weatherByHoursList: List<WeatherByHours>) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MainBlockColor)
    ) {
        itemsIndexed(weatherByHoursList) { index, currentHour ->
            WeatherHourItem(index, currentHour)
        }
    }
}

@Composable
fun WeatherHourItem(index: Int, currentHour: WeatherByHours) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val partOfDayText = arrayOf("Night", "Morning", "Afternoon", "Evening")

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
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

