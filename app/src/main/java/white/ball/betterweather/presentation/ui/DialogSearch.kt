package white.ball.betterweather.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData

@Composable
fun DialogSearch(
    dialogState: MutableLiveData<Boolean>,
    confirmCity: (String) -> Unit) {
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