package com.example.registration.view.signupScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.registration.R
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    isProfileSelected: Boolean,
    selectedImageUri: Uri?,
    chooseProfileImage: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isProfileSelected) {

            Image(
                modifier = Modifier
                    .clickable {
                        chooseProfileImage()
                    },
                painter = painterResource(id = R.drawable.profile_img),
                contentDescription = "profile",
            )
        } else if (selectedImageUri!=null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text = "Profile",
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun SignupEmail(
    modifier: Modifier = Modifier,
    isPrimaryEmailSelected: Boolean,
    selectEmail: (idx: Int) -> Unit,
    closeButtonClick: () -> Unit,
    primaryEmailIndex: Int,
) {

    val values = remember {
        mutableStateListOf("")
    }

    values.forEachIndexed { index, key ->
        CustomOutlinedInput(
            itemNo = (index + 1).toString(),
            text = values[index],
            onTextChanged = { values[index] = it },
            keyBoardType = KeyboardType.Email,
            label = "Email"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isPrimaryEmailSelected) {
                Button(
                    onClick = {
                        selectEmail(index)
                    }
                ) {
                    Text(text = "Make this as primary E-mail")
                }
            }
            if (isPrimaryEmailSelected && index == primaryEmailIndex) {
                IconButton(
                    modifier = Modifier
                        .size(30.dp),
                    onClick = { closeButtonClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_ic),
                        contentDescription = "close"
                    )
                }
            }


        }

    }

    IconButton(
        onClick = {
            values.add("")

        }
    ) {
        Icon(painter = painterResource(id = R.drawable.add_ic), contentDescription = "add")
    }


}

@Composable
fun SignupPhone(
    modifier: Modifier = Modifier,
    isPrimaryPhoneSelected: Boolean,
    selectPhone: (idx: Int) -> Unit,
    closeButtonClick: () -> Unit,
    primaryPhoneIndex: Int,
) {
    val valuesPhone = remember {
        mutableStateListOf("")
    }

    valuesPhone.forEachIndexed { index, key ->
        CustomOutlinedInput(
            itemNo = (index + 1).toString(),
            text = valuesPhone[index],
            onTextChanged = { valuesPhone[index] = it },
            keyBoardType = KeyboardType.Phone,
            label = "Phone number"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isPrimaryPhoneSelected) {
                Button(onClick = { selectPhone(index) }) {
                    Text(text = "Make this as primary phone")
                }
            }

            if (isPrimaryPhoneSelected && index == primaryPhoneIndex) {
                IconButton(
                    modifier = Modifier
                        .size(30.dp),
                    onClick = { closeButtonClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_ic),
                        contentDescription = "close"
                    )
                }
            }
        }
    }

    IconButton(
        onClick = {
            valuesPhone.add("")

        }
    ) {
        Icon(painter = painterResource(id = R.drawable.add_ic), contentDescription = "add")
    }


}

@Composable
fun CustomOutlinedInput(
    itemNo: String = "",
    text: String,
    onTextChanged: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text,
    label: String,
    minLines: Int = 1,
    maxLines: Int = 1,
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
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        minLines = minLines,
        maxLines = maxLines
    )


}


@Composable
fun DatePickerBar(
    text: String,
    onClick: () -> Unit,
    selectedDate: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.Black.copy(alpha = 0.6f))

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(1f),
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onClick()
                        Log.i("date click", "click")
                    }
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date",
            )

        }

        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp),
            text = selectedDate,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ),


            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onClick: (milli: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedDateInMillis = datePickerState.selectedDateMillis

    Column(
        modifier = Modifier
            .fillMaxSize(),

        ) {

//        Dialog(
//            onDismissRequest = { onDismiss() },
//            properties = DialogProperties(
//                dismissOnClickOutside = true,
//                dismissOnBackPress = true
//            )
//
//        ) {
        Column(
            modifier = Modifier
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier


            )
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        onClick(
                            convertMillisToDate(selectedDateInMillis)
                        )
//                            Log.i("date",selectedDate.toString())
                        onDismiss()

                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
//        }

    }


}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(mill: Long?): String {
    val format = SimpleDateFormat("dd/MM/yyyy")

    return if (mill != null) format.format(Date(mill)) else ""
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun PriviewUtils() {
    val datePickerState = rememberDatePickerState()
//    CustomDatePicker(
//        datePickerState, onDismiss = {},
//        onClick = {}
//    )
//    DatePickerBar(onClick = {}, text = "Pick your date of birth", selectedDate = "03/11/2002")
//    SignupPhone(
//      selectPhone = {},
//        isPrimaryPhoneSelected =false,
//        closeButtonClick = {},
//        primaryPhoneIndex = 0
//
//    )

//    UserProfile (
//        chooseProfileImage = {},
//        selectedImageUri =
//    )
}