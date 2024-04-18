package com.example.registration.view.signupScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.view.utils.CustomPassword


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
    var isDialogBoxOpen by remember {
        mutableStateOf(false)
    }
    var address by remember {
        mutableStateOf("")
    }
    var selectedDate by remember {
        mutableStateOf("")
    }
    val datePickerState = rememberDatePickerState()

    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        UserProfile()

        CustomOutlinedInput(
            text = firstName,
            onTextChanged = { firstName = it },
            label = "First name"
        )

        CustomOutlinedInput(
            text = lastName,
            onTextChanged = { lastName = it },
            label = "Last name"
        )

        SignupEmail()
        SignupPhone()

        CustomOutlinedInput(
            text = age,
            onTextChanged = { age = it },
            label = "Age",
            keyBoardType = KeyboardType.Phone
        )
        DatePickerBar(
            text = "Pick your date of birth",

            onClick = { isDialogBoxOpen = !isDialogBoxOpen },
            selectedDate = selectedDate
        )

        CustomOutlinedInput(
            text = address,
            onTextChanged = { address = it },
            label = "Enter your address",
            minLines = 3,
            maxLines = 5
        )

        CustomOutlinedInput(
            text = password,
            onTextChanged ={password = it} ,
            label = "Password"
        )

        CustomOutlinedInput(
            text = confirmPassword,
            onTextChanged ={confirmPassword = it} ,
            label = "confirm password"
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ })
        {
            Text(
                text = "Sign up",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }


        if (isDialogBoxOpen) {
            CustomDatePicker(
                datePickerState = datePickerState,
                onDismiss = { isDialogBoxOpen = !isDialogBoxOpen },
                onClick = {selectedDate = it}
            )

        }


    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSignUp() {
    SignupScreen()
}


//@SuppressLint("StateFlowValueCalledInComposition")
//@Composable
//fun SignupScreenTest(modifier: Modifier = Modifier) {
//
//    val dynamicTextFieldsDetails = hashMapOf(0 to "")
//
//    val dynamicTFD: SnapshotStateMap<Int, String> = remember {
//        mutableStateMapOf()
//    }
//
//    var textFieldsValue by remember {
//        mutableStateOf("")
//    }
//    Log.i("hash", dynamicTFD.size.toString())
//    Column {
//        LazyColumn {
//            itemsIndexed(dynamicTFD.keys.toList()) { index, key ->
//                Log.i("hash", " index : $index key $key value ${dynamicTFD[key]}")
//                Item(key, dynamicTFD[key].toString(), { dynamicTFD[key] = it })
//            }
//
//
//        }
//
//        Button(
//            onClick = {
//                val key = if (dynamicTFD.isEmpty()) 0 else dynamicTFD.size
//                dynamicTFD.put(key = key, value = key.toString())
//            }
//        ) {
//
//        }
//    }
//
//}