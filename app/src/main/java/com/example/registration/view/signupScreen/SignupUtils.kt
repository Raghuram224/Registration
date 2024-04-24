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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
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
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (selectedImageType) {
            0 -> {
                Image(
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
                        .clip(RoundedCornerShape(50))
                        .size(MaterialTheme.dimens.signupDimension.profileSize)
                        .clickable {
                            isDropDownExpanded = true
                            chooseProfileImage()
                        },
                    painter = painterResource(id = R.drawable.profile_img),
                    contentDescription = "profile",
                )
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
                    Image(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(50))
                            .size(MaterialTheme.dimens.signupDimension.profileSize)
                            .clickable {
                                isDropDownExpanded = true
                                chooseProfileImage()
                            },
                        painter = painterResource(id = R.drawable.profile_img),
                        contentDescription = "profile",
                    )
                }
            }

            2 -> {
                if (selectedCameraImage != null) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
                            .clip(RoundedCornerShape(50.dp))
                            .size(MaterialTheme.dimens.signupDimension.profileSize)
                            .clickable {
                                isDropDownExpanded =true
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



        Text(
            modifier = Modifier
                .padding(MaterialTheme.dimens.signupDimension.padding04)
                .fillMaxWidth(),
            text = "Profile",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.normalFont16,
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
    emailList: SnapshotStateList<String>,
    isFieldError: SnapshotStateList<Boolean>,
    removeField:(idx:Int)->Unit,

) {


    emailList.forEachIndexed { index, key ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {
            CustomTextField(
                itemNo = (index + 1).toString(),
                text = emailList[index],
                onTextChanged = { emailList[index] = it },
                keyBoardType = KeyboardType.Email,
                label = "Email",
                isError = if(isFieldError.isEmpty()) isFieldError.add(false) else isFieldError[index] ,
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth()
            )

            if (!isPrimaryEmailSelected) {
                IconButton(onClick = { selectEmail(index) }) {
                    Image(
                        modifier = Modifier
                            .weight(0.1f)
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.primary_ic),
                        contentDescription = "primary email",
                    )
                }

            }
            if (isPrimaryEmailSelected && index == primaryEmailIndex) {
                IconButton(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(30.dp),
                    onClick = { closeButtonClick() }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.close_ic),
                        contentDescription = "non primary"
                    )
                }
            }
            if (isPrimaryEmailSelected && index!=primaryEmailIndex){
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

    IconButton(
        onClick = {
            emailList.add("")
            isFieldError.add(false)


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
    phoneList: SnapshotStateList<String>,
    isFieldError: SnapshotStateList<Boolean>,
    removeField: (idx: Int) -> Unit,
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

                )
            if (!isPrimaryPhoneSelected) {
                IconButton(onClick = { selectPhone(index) }) {
                    Image(
                        modifier = Modifier
                            .weight(0.1f)
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.primary_ic),
                        contentDescription = "primary email",
                    )
                }

            }

            if (isPrimaryPhoneSelected && index == primaryPhoneIndex) {
                IconButton(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(30.dp),
                    onClick = { closeButtonClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_ic),
                        contentDescription = "close"
                    )
                }
            }
            if (isPrimaryPhoneSelected && index!=primaryPhoneIndex){
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
    IconButton(
        onClick = {
            phoneList.add("")
            isFieldError.add(false)

        }
    ) {
        Icon(painter = painterResource(id = R.drawable.add_ic), contentDescription = "add")
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
    visualTransformation: VisualTransformation = VisualTransformation.None

) {

    OutlinedTextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .fillMaxWidth()

            .onFocusChanged {
                focusChanged(it)
            },
        value = text,
        onValueChange = {
            onTextChanged.invoke(it)
        },
        label = { Text(text = "$label $itemNo") },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = ImeAction.Done),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        visualTransformation = visualTransformation,

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
            onTextChanged.invoke(it)
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.dimens.signupDimension.normalFont16
        ),
        placeholder = { Text(text = "$label $itemNo") },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        minLines = minLines,
        maxLines = maxLines,
        singleLine = true,
        isError = isError,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            disabledIndicatorColor = Color.Transparent
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

    ) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .fillMaxWidth(),

        value = text,
        onValueChange = {
            onTextChanged.invoke(it)
        },
        label = { Text(text = "$label $itemNo") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.eye_close_ic
            else R.drawable.eye_open_ic

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = image), contentDescription = "password")
            }
        }

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
            .border(1.dp, Color.Black.copy(alpha = 0.6f))

    ) {
        Row(
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
                    fontSize = MaterialTheme.dimens.signupDimension.normalFont16,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onClick()
                        Log.i("date click", "click")
                    }
                    .padding(
                        vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                        horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                    ),
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date",
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
    Log.i("Day1", milliseconds.toString())
    val totalSeconds = milliseconds / 1000
    Log.i("Day2", totalSeconds.toString())
    val minutes = totalSeconds / 60
    Log.i("Day3", minutes.toString())
    val hour = minutes / 60
    Log.i("Day4", hour.toString())
    val day = hour / 24
    Log.i("Day5", day.toString())
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


