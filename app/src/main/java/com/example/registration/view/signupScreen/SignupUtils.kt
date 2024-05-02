package com.example.registration.view.signupScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.registration.ui.theme.titleStyle
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    imageBitmap: Bitmap?,
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
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )

        if (imageBitmap != null) {
            AsyncImage(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.signupDimension.pageVerticalPadding08)
                    .clip(RoundedCornerShape(50))
                    .size(MaterialTheme.dimens.signupDimension.profileSize)
                    .clickable {
                        isDropDownExpanded = true
                        chooseProfileImage()
                    },
                model = imageBitmap,
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

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = isDropDownExpanded,
        onExpandedChange = { isDropDownExpanded = it },

        ) {


        ExposedDropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = { isDropDownExpanded = false },

            ) {
            DropdownMenuItem(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                text = { Text(text = "Camera") },
                onClick = {
                    openCamera()
                    isDropDownExpanded = false
                },
                leadingIcon = {
                    Image(imageVector = Icons.Default.Camera, contentDescription = "Camera")
                })
            DropdownMenuItem(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                text = { Text(text = "Gallery") },
                onClick = {
                    openGallery()
                    isDropDownExpanded = false
                },
                leadingIcon = {
                    Image(imageVector = Icons.Default.Photo, contentDescription = "Gallery")
                })
            if (isProfileSelected || imageBitmap!=null) {

                DropdownMenuItem(modifier = Modifier
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
    emailFocusRequester: FocusRequester,


    ) {


    emailList.forEachIndexed { index, key ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {
            CustomTextField(
                modifier = Modifier
                    .weight(0.9f)
                    .focusRequester(focusRequester = emailFocusRequester)
                    .fillMaxWidth(),
                itemNo = (index + 1).toString(),
                text = emailList[index],
                onTextChanged = { emailList[index] = it },
                keyBoardType = KeyboardType.Email,
                label = "Email",
                isError = isFieldError[index],
                regex = regex,
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
                    onClick = { removeField(index) }) {
                    Image(
                        painter = painterResource(id = R.drawable.minus_ic),
                        contentDescription = "remove field"
                    )
                }
            }

        }

    }
    Row(modifier = Modifier
        .clickable {
            emailList.add("")
            isFieldError.add(false)
        }
        .padding(MaterialTheme.dimens.signupDimension.padding08),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(30.dp),
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
    phoneFocusRequester: FocusRequester
) {

    phoneList.forEachIndexed { index, key ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomTextField(
                modifier = Modifier
                    .focusRequester(focusRequester = phoneFocusRequester)
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
                    onClick = { removeField(index) }) {
                    Image(
                        painter = painterResource(id = R.drawable.minus_ic),
                        contentDescription = "remove field"
                    )
                }
            }
        }

    }

    Row(modifier = Modifier
        .clickable {
            phoneList.add("")
            isFieldError.add(false)
        }
        .padding(MaterialTheme.dimens.signupDimension.padding08),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(30.dp),
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
    regex: String,
    updateFocusChangeValue: () -> Unit = {},
    readOnly: Boolean = false,
    textColor: Color = Blue,
    focusedIndicatorColor:Color = Blue,
    unfocusedIndicatorColor:Color = Color.Black.copy(alpha = 0.1f),


) {

    TextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding16
            )
            .fillMaxWidth()

            .onFocusChanged {
                if (!it.hasFocus) {
                    updateFocusChangeValue()
                }
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
                text = "$label $itemNo", color = Color.Black.copy(alpha = 0.3f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = ImeAction.Done),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = focusedIndicatorColor,
            unfocusedIndicatorColor =unfocusedIndicatorColor,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        ),
        textStyle = MaterialTheme.typography.body1.titleStyle()

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
        textStyle = MaterialTheme.typography.body1.titleStyle(),
        placeholder = {
            Text(
                text = "$label $itemNo", color = Color.Black.copy(alpha = 0.3f)
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
            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.1f),
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        ),

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
                text = "$label $itemNo", color = Color.Black.copy(alpha = 0.3f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) R.drawable.eye_close_ic
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
            focusedIndicatorColor = Blue,
            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.1f),
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        ),
        textStyle = MaterialTheme.typography.body1.titleStyle(),

        )


}

@Composable
fun DatePickerBar(
    text: String, onClick: () -> Unit, selectedDate: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            )
            .clip(RoundedCornerShape(5.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Column(
            modifier = Modifier.clickable {
                onClick()
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.dimens.signupDimension.padding04),
                text = text,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = MaterialTheme.dimens.signupDimension.normalFont16,

                    )
            )

            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.calendar_ic),
                contentDescription = "Calendar"
            )


        }

        Text(
            modifier = Modifier.padding(
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
        modifier = Modifier.background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        DatePicker(
            state = datePickerState, modifier = Modifier
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
            TextButton(onClick = {

                if (selectedDateInMillis != null) {
                    updateAge(milliToYears(Date().time.minus(selectedDateInMillis)))
                }
                onClick(
                    convertMillisToDate(selectedDateInMillis)
                )
                onDismiss()

            }) {
                Text(text = "Confirm")
            }
        }
    }


}

@Composable
fun CustomColumnCardCreator(
    modifier: Modifier,
    anyComposable: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.padding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding04
            ),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.signupDimension.padding08,
                    vertical = MaterialTheme.dimens.signupDimension.padding08
                )
        ) {
            anyComposable()
        }
    }
}

@Composable
fun CustomRowCardCreator(
    modifier: Modifier,
    anyComposable: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.padding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding04
            ),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(
                    horizontal = MaterialTheme.dimens.signupDimension.padding08,
                    vertical = MaterialTheme.dimens.signupDimension.padding08
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            anyComposable()
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


data class UserDetails(
    var firstName: String,
    var lastName: String,
    var age: String,
    var address: String,
    var dob: String,
    var primaryEmail: String,
    var primaryPhone: String,
    var otherEmails: String?,
    var otherPhones: String?,
    var website: String,
    var password: String,
    var profileImage:Bitmap?

    )


enum class Keyboard {
    Opened, Closed
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


@RequiresApi(Build.VERSION_CODES.P)
fun convertUriToBitmapAboveAndroidP(uri: Uri, activity: Activity): Bitmap {
    val source = ImageDecoder.createSource(activity.contentResolver, uri)
    return ImageDecoder.decodeBitmap(source)
}

fun convertUriToBitmapBelowAndroidP(uri: Uri, activity: Activity): Bitmap {
    return MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
}
