package com.example.registration.view.signupScreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    selectedImageType: Int,
    selectedImageUri: Uri?,
    selectedCameraImage: Bitmap?,
    chooseProfileImage: () -> Unit,
    isProfileSelected: Boolean,
    openCamera: () -> Unit,
    openGallery: () -> Unit,
    removeProfile: () -> Unit,

    ) {
    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
        when (selectedImageType) {
            0 -> {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50))
                        .background(White)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .size(50.dp)
                            .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
                            .size(MaterialTheme.dimens.signupDimension.profileSize)
                            .clickable {
                                isDropDownExpanded = true
                                chooseProfileImage()
                            },
                        painter = painterResource(id = R.drawable.add_ic),
                        contentDescription = "profile",
                    )
                }

            }

            1 -> {
                if (selectedImageUri != null) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.dimens.signupDimension.pageVerticalPadding08)
                            .clip(RoundedCornerShape(50))
                            .size(MaterialTheme.dimens.signupDimension.profileSize)
                            .clickable {
                                isDropDownExpanded = true
                                chooseProfileImage()
                            },
                        model = selectedImageUri,
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50))
                            .background(White)
                    ) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .size(50.dp)
                                .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
                                .size(MaterialTheme.dimens.signupDimension.profileSize)
                                .clickable {
                                    isDropDownExpanded = true
                                    chooseProfileImage()
                                },
                            painter = painterResource(id = R.drawable.add_ic),
                            contentDescription = "profile",
                        )
                    }
                }
            }

            2 -> {
                if (selectedCameraImage != null) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
                            .size(MaterialTheme.dimens.signupDimension.profileSize)
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                isDropDownExpanded = true
                                chooseProfileImage()
                            },
                        bitmap = selectedCameraImage.asImageBitmap(),
                        contentDescription = "profile",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

        }




        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = it },

            ) {


            ExposedDropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },

                ) {
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    text = { Text(text = "Camera") },
                    onClick = {
                        openCamera()
                        isDropDownExpanded = false
                    },
                    leadingIcon = {
                        Image(imageVector = Icons.Default.Camera, contentDescription = "Camera")
                    }
                )
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    text = { Text(text = "Gallery") },
                    onClick = {
                        openGallery()
                        isDropDownExpanded = false
                    },
                    leadingIcon = {
                        Image(imageVector = Icons.Default.Photo, contentDescription = "Gallery")
                    }
                )
                if (isProfileSelected) {

                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = { Text(text = "Remove") },
                        onClick = {
                            removeProfile()
                            isDropDownExpanded = false
                        },
                        leadingIcon = {
                            Image(imageVector = Icons.Default.Person, contentDescription = "Remove")
                        }
                    )
                }
            }
        }


//
//        Text(
//            modifier = Modifier
//                .padding(MaterialTheme.dimens.signupDimension.padding04)
//                .fillMaxWidth(),
//            text = "Profile",
//            style = TextStyle(
//                fontSize = MaterialTheme.dimens.signupDimension.normalFont16,
//                textAlign = TextAlign.Center
//            )
//        )
    }


}


@Composable
fun SignupEmail(
    modifier: Modifier = Modifier,
    isPrimaryEmailSelected: Boolean,
    selectEmail: (idx: Int) -> Unit,
    closeButtonClick: () -> Unit,
    primaryEmailIndex: Int,
    emailList: MutableList<String>,
    isFieldError: SnapshotStateList<Boolean>,
    removeField: (idx: Int) -> Unit,
    regex: String,


    ) {


    emailList.forEachIndexed { index, key ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {
            CustomTextField(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth(),
                itemNo = (index + 1).toString(),
                text = emailList[index],
                onTextChanged = { emailList[index] = it },
                keyBoardType = KeyboardType.Email,
                label = "Email",
                isError = if (isFieldError.isEmpty()) isFieldError.add(false) else isFieldError[index],
                regex = regex
            )

            if (!isPrimaryEmailSelected) {
                IconButton(onClick = { selectEmail(index) }) {
                    Image(
                        modifier = Modifier
                            .weight(0.1f)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.primary_ic),
                        contentDescription = "primary email",
                    )
                }

            }
            if (isPrimaryEmailSelected && index == primaryEmailIndex) {

                Image(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp)
                        .clickable {
                            closeButtonClick()
                        },
                    painter = painterResource(id = R.drawable.close_ic),
                    contentDescription = "non primary"
                )

            }
            if (isPrimaryEmailSelected && index != primaryEmailIndex) {
                IconButton(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(30.dp),
                    onClick = { removeField(index) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.minus_ic),
                        contentDescription = "remove field"
                    )
                }
            }

        }

    }
    Row(
        modifier = Modifier
            .clickable {
                emailList.add("")
                isFieldError.add(false)
            }
            .padding(MaterialTheme.dimens.signupDimension.padding08),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(id = R.drawable.add_ic),
            contentDescription = ""
        )
        Text(text = "Add an email")
    }


}

@Composable
fun SignupPhone(
    modifier: Modifier = Modifier,
    isPrimaryPhoneSelected: Boolean,
    selectPhone: (idx: Int) -> Unit,
    closeButtonClick: () -> Unit,
    primaryPhoneIndex: Int,
    phoneList: MutableList<String>,
    isFieldError: SnapshotStateList<Boolean>,
    removeField: (idx: Int) -> Unit,
    regex: String,
) {

    phoneList.forEachIndexed { index, key ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomTextField(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth(),
                itemNo = (index + 1).toString(),
                text = phoneList[index],
                onTextChanged = { phoneList[index] = it },
                keyBoardType = KeyboardType.Phone,
                label = "Phone number",
                isError = isFieldError[index],
                regex = regex

            )
            if (!isPrimaryPhoneSelected) {
                IconButton(onClick = { selectPhone(index) }) {
                    Image(
                        modifier = Modifier
                            .weight(0.1f)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.primary_ic),
                        contentDescription = "primary email",
                        colorFilter = ColorFilter.tint(Blue)
                    )
                }

            }

            if (isPrimaryPhoneSelected && index == primaryPhoneIndex) {

                Image(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp)
                        .clickable {
                            closeButtonClick()
                        },
                    painter = painterResource(id = R.drawable.close_ic),
                    contentDescription = "close",
                    colorFilter = ColorFilter.tint(Blue)
                )

            }
            if (isPrimaryPhoneSelected && index != primaryPhoneIndex) {
                IconButton(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(30.dp),
                    onClick = { removeField(index) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.minus_ic),
                        contentDescription = "remove field"
                    )
                }
            }
        }

    }

    Row(
        modifier = Modifier
            .clickable {
                phoneList.add("")
                isFieldError.add(false)
            }
            .padding(MaterialTheme.dimens.signupDimension.padding08),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(id = R.drawable.add_ic),
            contentDescription = ""
        )
        Text(text = "Add an phone")
    }

}


@Composable
fun CustomOutlinedInput(
    modifier: Modifier = Modifier,
    itemNo: String = "",
    text: String,
    onTextChanged: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text,
    label: String,
    minLines: Int = 1,
    maxLines: Int = 1,
    focusChanged: (FocusState) -> Unit = {},
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    regex: String

) {

    TextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding16
            )
            .fillMaxWidth()

            .onFocusChanged {
                focusChanged(it)
            },
        value = text,
        onValueChange = {
            if (it.matches(regex = Regex(regex))) {

                onTextChanged.invoke(it)
            }
        },
        placeholder = {
            Text(
                text = "$label $itemNo",
                color = Color.Black.copy(alpha = 0.3f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = ImeAction.Done),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Blue,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        )

    )


}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    itemNo: String = "",
    text: String,
    onTextChanged: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text,
    label: String,
    minLines: Int = 1,
    maxLines: Int = 1,
    isError: Boolean = false,
    regex: String,


    ) {

    TextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            if (it.matches(regex = Regex(regex))) {

                onTextChanged.invoke(it)
            }

        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.dimens.signupDimension.normalFont16
        ),
        placeholder = {
            Text(
                text = "$label $itemNo",
                color = Color.Black.copy(alpha = 0.3f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        minLines = minLines,
        maxLines = maxLines,
        singleLine = true,
        isError = isError,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Blue,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Blue,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        )
    )
}

@Composable
fun CustomOutlinedPasswordInput(
    modifier: Modifier = Modifier,
    itemNo: String = "",
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    minLines: Int = 1,
    maxLines: Int = 1,
    isError: Boolean = false,
    regex: String,

    ) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    TextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .fillMaxWidth(),

        value = text,
        onValueChange = {
            if (it.matches(regex = Regex(regex))) {

                onTextChanged.invoke(it)
            }
        },
        label = {
            Text(
                text = "$label $itemNo",
                color = Color.Black.copy(alpha = 0.3f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.eye_close_ic
            else R.drawable.eye_open_ic
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        passwordVisible = !passwordVisible
                    },
                painter = painterResource(id = image),
                contentDescription = "password",
                colorFilter = ColorFilter.tint(Blue),
            )

        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Blue,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        )

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
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .clip(RoundedCornerShape(5.dp))


    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.signupDimension.padding04)
                    .fillMaxWidth()
                    .weight(1f),
                text = text,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = MaterialTheme.dimens.signupDimension.normalFont16,

                    )
            )

            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.calendar_ic),
                contentDescription = "Calendar"
            )


        }

        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = selectedDate,
            style = TextStyle(
                color = Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.dimens.signupDimension.normalFont16
            ),


            )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onClick: (milli: String) -> Unit,
    updateAge: (milli: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedDateInMillis = datePickerState.selectedDateMillis


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
                .padding(MaterialTheme.dimens.signupDimension.padding04)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
            TextButton(
                onClick = {

                    if (selectedDateInMillis != null) {
                        updateAge(milliToYears(Date().time.minus(selectedDateInMillis)))
                    }
                    onClick(
                        convertMillisToDate(selectedDateInMillis)
                    )
                    onDismiss()

                }
            ) {
                Text(text = "Confirm")
            }
        }
    }


}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(mill: Long?): String {
    val format = SimpleDateFormat("dd/MM/yyyy")

    return if (mill != null) format.format(Date(mill)) else ""
}

fun milliToYears(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val hour = minutes / 60
    val day = hour / 24
    val year = (day / 365)

    return year.toString()
}


data class SignupDetails(
    val firstName: String,
    val lastName: String,
    val age: String,
    val address: String,
    val dob: String,
    val primaryEmail: String,
    val primaryPhone: String,
    val otherEmails: String,
    val otherPhones: String,

    )

enum class Keyboard {
    Opened,
    Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val currentView = LocalView.current
    DisposableEffect(currentView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            currentView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = currentView.rootView.height
            val keyboardHeight = screenHeight - rect.bottom

            keyboardState.value =
                if (keyboardHeight > screenHeight * 0.15) Keyboard.Opened else Keyboard.Closed
        }
        currentView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            currentView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }


    return keyboardState
}


