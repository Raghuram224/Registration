package com.example.registration.view.signupScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.registration.ui.theme.dimens
import com.example.registration.view.utils.CameraPreview
import com.example.registration.view.utils.PhotoBottomSheetContent
import com.example.registration.viewModels.SignupViewModel
import com.example.registration.viewModels.TextFieldType
import com.example.registration.viewModels.TextFieldTypeTest
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel,
    navController: NavController,
) {
    val activity = LocalContext.current as Activity
    val scrollState = rememberScrollState()

    val firstName by signupViewModel.firstName.collectAsStateWithLifecycle()
    val lastName by signupViewModel.lastName.collectAsStateWithLifecycle()

    val age by signupViewModel.age.collectAsStateWithLifecycle()

    val address by signupViewModel.address.collectAsStateWithLifecycle()

    val dateOfBirth by signupViewModel.dob.collectAsStateWithLifecycle()

    var emailList = signupViewModel.emailList
    val phoneList = signupViewModel.phoneList

    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isDatePickerSheetOpen by remember {
        mutableStateOf(false)
    }
    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val datePickerState = rememberDatePickerState()

    var isPrimaryEmailSelected by remember {
        mutableStateOf(true)
    }
    var primaryEmailIndex by remember {
        mutableStateOf(0)
    }
    var isPrimaryPhoneSelected by remember {
        mutableStateOf(true)
    }
    var primaryPhoneIndex by remember {
        mutableStateOf(0)
    }

    // camera essentials

    var isProfileSheetOpen by remember {
        mutableStateOf(false)
    }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? -> selectedImageUri = uri }
    )
    var isProfileSelected by remember {
        mutableStateOf(false)
    }

    var isCameraSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowImagesSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val cameraSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val takenPicturesSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                androidx.camera.view.CameraController.IMAGE_CAPTURE
            )

        }
    }
    var selectedImageType by remember {
        mutableStateOf(0)
    }

    val bitmaps by signupViewModel.bitmaps.collectAsState()
    val selectedCameraImage by signupViewModel.selectedCameraImage.collectAsState()


    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    //Fields color
    var fNameColor by remember {
        mutableStateOf(false)
    }
    var lNameColor by remember {
        mutableStateOf(false)
    }
    var emailListColor = remember {
        mutableStateListOf(false)
    }
    val phoneListColor = remember {
        mutableStateListOf(false)
    }
    val addressColor by remember {
        mutableStateOf(false)
    }
    var passwordColor by remember {
        mutableStateOf(false)
    }
    var confirmPasswordColor by remember {
        mutableStateOf(false)
    }


    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    val keyBoardState by keyboardAsState()
    Log.i("keyboard", keyBoardState.name)

    var isAgeFocused by remember {
        mutableStateOf(false)
    }

    val signupDataTest = signupViewModel.signupData.collectAsState()



    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = MaterialTheme.dimens.signupDimension.pageHorizontalPadding16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        UserProfile(
            chooseProfileImage = {
                isProfileSheetOpen = true

            },
            selectedImageUri = selectedImageUri,
            selectedImageType = selectedImageType,
            selectedCameraImage = selectedCameraImage,
            isProfileSelected = isProfileSelected,
            openCamera = {
                if (!hasRequiredPermission(mContext = context, PERMISSIONS = PERMISSIONS)) {
                    ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS,
                        0
                    )
                }

                if (hasRequiredPermission(
                        mContext = context,
                        PERMISSIONS = PERMISSIONS
                    )
                ) {
                    isCameraSheetOpen = true

                } else {
                    Toast
                        .makeText(
                            context,
                            "need camera permission",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            },
            openGallery = {
                isProfileSelected = true
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                selectedImageType = 1
            },
            removeProfile = {
                isProfileSelected = false
                selectedImageType = 0
            }

        )

        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "User name",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )
        CustomOutlinedInput(
            text = firstName,
            onTextChanged = {
                signupViewModel.updateText(it, TextFieldType.FirstName)
                signupViewModel.updateSignupDataTest(it, TextFieldTypeTest.FirstName)

            },
            label = "First name",
            isError = fNameColor
        )

        CustomOutlinedInput(
            text = lastName,
            onTextChanged = {
                signupViewModel.updateText(it, TextFieldType.LastName)
                signupViewModel.updateSignupDataTest(it, TextFieldTypeTest.LastName)
            },
            label = "Last name",
            isError = lNameColor

        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "Email",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )

        SignupEmail(
            selectEmail = {
                primaryEmailIndex = it
                isPrimaryEmailSelected = true
            },
            isPrimaryEmailSelected = isPrimaryEmailSelected,
            closeButtonClick = {
                primaryEmailIndex = 0
                isPrimaryEmailSelected = false
            },
            primaryEmailIndex = primaryEmailIndex,
            emailList = emailList,
            isFieldError = emailListColor,
            removeField = {
                if (emailList.size > 1 && it != primaryEmailIndex) {

                    if (primaryEmailIndex == 1) {
                        emailList.removeAt(0)
                        emailListColor.removeAt(0)
                        primaryEmailIndex = 0
                    } else {

                        emailList.removeAt(it)
                        emailListColor.removeAt(it)

                    }

                }

            },

            )
        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "Phone",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )
        SignupPhone(
            selectPhone = {
                primaryPhoneIndex = it
                isPrimaryPhoneSelected = true
            },
            isPrimaryPhoneSelected = isPrimaryPhoneSelected,
            closeButtonClick = {
                primaryPhoneIndex = 0
                isPrimaryPhoneSelected = false
            },
            primaryPhoneIndex = primaryPhoneIndex,
            phoneList = phoneList,
            isFieldError = phoneListColor,
            removeField = {
                if (phoneList.size > 1 && it != primaryPhoneIndex) {
                    if (primaryPhoneIndex == 1) {
                        phoneList.removeAt(0)
                        phoneListColor.removeAt(0)
                        primaryPhoneIndex = 0
                    } else {

                        phoneList.removeAt(it)
                        phoneListColor.removeAt(it)

                    }

                }
            }

        )
//        Log.i("Test",(age.toInt()+5).toString())


        LaunchedEffect(key1 = keyBoardState) {
            if (keyBoardState == Keyboard.Closed && isAgeFocused) {
                focusManager.clearFocus()
                signupViewModel.updateText(
                    text = if (age != null) convertMillisToDate(Date().time.minus(yearsToMillis(age.toLong()))) else "0",
                    TextFieldType.DOB,
                )
                signupViewModel.updateSignupDataTest(
                    text = if (age != null) convertMillisToDate(Date().time.minus(yearsToMillis(age.toLong()))) else "0",
                    TextFieldTypeTest.DOB,)
            }
        }
        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "Age",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )

        CustomOutlinedInput(
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester),
            text = age,
            onTextChanged = {
                signupViewModel.updateText(it, TextFieldType.Age)
                signupViewModel.updateSignupDataTest(it, TextFieldTypeTest.Age)
            },
            label = "Age",
            keyBoardType = KeyboardType.Phone,
            focusChanged = { state ->
                isAgeFocused = state.isFocused
            },

            )

        DatePickerBar(
            text = "Pick your date of birth",
            onClick = { isDatePickerSheetOpen = true },
            selectedDate = dateOfBirth
        )

        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "Address",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )

        CustomOutlinedInput(
            text = address,
            onTextChanged = {
                signupViewModel.updateText(text = it, TextFieldType.Address)
                signupViewModel.updateSignupDataTest(text = it, TextFieldTypeTest.Address)
                            },
            label = "Enter your address",
            minLines = 3,
            maxLines = 5,
            isError = addressColor
        )

        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                ),
            text = "Password",
            style = TextStyle(
                fontSize = MaterialTheme.dimens.signupDimension.headingFont20
            )
        )

        CustomOutlinedPasswordInput(
            text = password,
            onTextChanged = { password = it },
            label = "Password",
            isError = passwordColor,


            )

        CustomOutlinedPasswordInput(
            text = confirmPassword,
            onTextChanged = { confirmPassword = it },
            label = "confirm password",
            isError = confirmPasswordColor,


            )

        Button(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                )
                .fillMaxWidth(),
            onClick = {

                emailListColor[primaryEmailIndex] = emailList[primaryEmailIndex].isEmpty()
                phoneListColor[primaryPhoneIndex] = phoneList[primaryPhoneIndex].isEmpty()

                fNameColor = firstName.isEmpty()
                lNameColor = lastName.isEmpty()
                passwordColor = password.isEmpty()
                confirmPasswordColor = confirmPassword.isEmpty()

                if (
                    signupViewModel.checkFieldsValue(
                        primaryEmail = emailList[primaryEmailIndex],
                        primaryPhone = phoneList[primaryPhoneIndex],
                        age = age,
                        dob = dateOfBirth,
                        firstName = firstName,
                        lastName = lastName
                    )
                ) {
                    if (signupViewModel.checkPassword(
                            password = password,
                            confirmPassword = confirmPassword
                        )
                    ) {

                        signupViewModel.signupDetails = SignupDetails(
                            firstName = firstName,
                            lastName = lastName,
                            primaryEmail = emailList[primaryEmailIndex],
                            primaryPhone = phoneList[primaryPhoneIndex],
                            age = age,
                            dob = dateOfBirth,
                            address = address,
                            otherEmails = signupViewModel.convertListToString(
                                list = emailList.toList(),
                                idx = primaryEmailIndex
                            ),
                            otherPhones = signupViewModel.convertListToString(
                                list = phoneList.toList(),
                                idx = primaryPhoneIndex
                            )
                        )

                        navController.navigate("DataScreen")

                    } else {
                        Toast.makeText(context, "check your password is same", Toast.LENGTH_SHORT)
                            .show()
                    }


                } else {
                    Toast.makeText(context, "Check fields value", Toast.LENGTH_SHORT).show()
                }

            }
        )
        {
            Text(
                text = "Sign up",
                style = TextStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.dimens.signupDimension.normalFont16
                )
            )
        }

        Text(text = signupDataTest.toString()) //Test


        if (isDatePickerSheetOpen) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxSize(1f),
                onDismissRequest = { isDatePickerSheetOpen = false },
                sheetState = datePickerSheetState,

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    CustomDatePicker(
                        datePickerState = datePickerState,
                        onDismiss = { isDatePickerSheetOpen = false },
                        onClick = {
                            signupViewModel.updateText(it, TextFieldType.DOB)
                            signupViewModel.updateSignupDataTest(it, TextFieldTypeTest.DOB)
                        },
                        updateAge = {
                            signupViewModel.updateText(it, TextFieldType.Age)
                            signupViewModel.updateSignupDataTest(it, TextFieldTypeTest.Age)
                        }
                    )
                }


            }

        }


        if (isCameraSheetOpen) {


            ModalBottomSheet(
                onDismissRequest = { isCameraSheetOpen = false },
                sheetState = cameraSheetState,
                modifier = Modifier
                    .fillMaxSize()

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    CameraPreview(
                        modifier = Modifier
                            .weight(0.9f)
                            .fillMaxSize(),
                        controller = cameraController
                    )

                    Row(
                        modifier = Modifier
                            .weight(0.1f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = {
                                takePhoto(
                                    controller = cameraController,
                                    mContext = context,
//                                    onPhotoTaken = { signupViewModel.takePhoto(bitmap =it) }
                                    onPhotoTaken = signupViewModel::takePhoto
                                )
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Camera,
                                contentDescription = "take picture"
                            )
                        }
                        IconButton(
                            onClick = {
                                isShowImagesSheetOpen = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Photo,
                                contentDescription = "Gallery"
                            )
                        }
                    }

                }

            }
        }
    }
    Log.i("bitmap", selectedCameraImage.toString())

    if (isShowImagesSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isShowImagesSheetOpen = false },
            sheetState = takenPicturesSheetState
        ) {

            PhotoBottomSheetContent(
                bitmaps = bitmaps,
                onClick = {
                    selectedImageType = 2
                    signupViewModel.selectCameraImage(bitmap = it)
                    isShowImagesSheetOpen = false
                    isCameraSheetOpen = false
                    isProfileSheetOpen = false
                    isProfileSelected = true
                }
            )
        }
    }


}


private fun takePhoto(
    controller: LifecycleCameraController,
    mContext: Context,
    onPhotoTaken: (bitmap: Bitmap) -> Unit,
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(mContext),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedImage = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                onPhotoTaken(rotatedImage)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)

                Log.i("Camera", "Error while taking photos", exception)
            }

        }
    )
}

private fun hasRequiredPermission(
    mContext: Context,
    PERMISSIONS: Array<String>
): Boolean {

    return PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            mContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}


fun yearsToMillis(years: Long): Long {
    val days = years * 365
    val hours = days * 24
    val minutes = hours * 60
    val seconds = minutes * 60
    val milliseconds = seconds * 1000
    return milliseconds
}


@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSignUp() {
//    SignupScreen()
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