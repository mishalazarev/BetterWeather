package white.ball.betterweather.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import white.ball.betterweather.presentation.ui.theme.MainBlockColor
import white.ball.betterweather.R
import androidx.compose.ui.res.painterResource


@Composable
fun SunInfo(
    sunrise: String,
    sunset: String
) {
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
                text = sunrise,
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
                text = sunset,
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
}