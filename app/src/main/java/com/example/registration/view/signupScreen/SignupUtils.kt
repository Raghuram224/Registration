package com.example.registration.view.signupScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.R

@Composable
fun UserProfile(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.profile_img),
            contentDescription ="profile"
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text ="Profile",
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun SignupEmail(modifier: Modifier = Modifier) {

    val values = remember {
        mutableStateListOf("")
    }

    values.forEachIndexed { index, key ->
        CustomOutlinedInput(itemNo = index,
            text = values[index],
            onTextChanged = { values[index] = it },
            keyBoardType = KeyboardType.Email,
            label = "Email"
        )
    }

    Button(
        onClick = {
            values.add("")
        }
    ) {
        Text(text = "Add another email")
    }

}

@Composable
fun SignupPhone(modifier: Modifier = Modifier) {
//    val keysPhone = remember {
//        mutableStateListOf(0)
//    }
    val valuesPhone = remember {
        mutableStateListOf("")
    }

    valuesPhone.forEachIndexed { index, key ->
        CustomOutlinedInput(itemNo = index,
            text = valuesPhone[index],
            onTextChanged = { valuesPhone[index] = it },
            keyBoardType = KeyboardType.Email,
            label = "Phone number"
        )
    }

    Button(
        onClick = {
            valuesPhone.add("")
//            valuesPhone.add("")
//            Log.i("list", keysPhone.toList().toString() + valuesPhone.toList().toString())
        }
    ) {
        Text(text = "Add another Phone")
    }

}