package com.example.registration.view.signupScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        UserProfile()
        OutlinedTextField(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxWidth(),
            value = firstName,
            label = { Text(text = "First name") },
            onValueChange = { firstName = it }
        )
        OutlinedTextField(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxWidth(),
            value = lastName,
            label = { Text(text = "Last name") },
            onValueChange = { lastName = it }
        )
        SignupEmail()
        SignupPhone()

    }
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

@Composable
fun CustomOutlinedInput(
    itemNo: Int,
    text: String,
    onTextChanged: (String) -> Unit,
    keyBoardType: KeyboardType,
    label: String,
) {
    var temp by remember {
        mutableStateOf(text)
    }
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused.not()) {
                    onTextChanged.invoke(temp)
                }
            },
        value = temp, onValueChange = { temp = it },
        label = { Text(text = "$label $itemNo") },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType)
    )

}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSignUp() {
    SignupScreen()
}