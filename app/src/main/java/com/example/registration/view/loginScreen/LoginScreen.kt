package com.example.registration.view.loginScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
        .background(Color.White)
) {
    val configuration = LocalConfiguration.current


    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT ->
            LoginPortrait(
                email = email,
                password = password,
                emailStringCallBack = { email = it },
                passwordStringCallback = { password = it }
            )
        Configuration.ORIENTATION_LANDSCAPE->{
            LoginLandScape(
                email = email,
                password = password ,
                emailStringCallBack = { email = it },
                passwordStringCallback = { password = it }
            )
        }
    }


}

fun checkEmailAndPassword(email: String, password: String): Boolean {

    val passRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
    val emailRegex = Regex("(?=.*[A-Za-z])(?=.*)\\S+@\\S+\\.\\S+")

    return emailRegex.matches(email) && passRegex.matches(password)
}


//@Preview(showSystemUi = true)
//@Composable
//private fun PreviewHomePortraitScreen() {
//    HomeScreen()
//}



@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun PreviewHomeLandScapeScreen() {
    LoginScreen()
}