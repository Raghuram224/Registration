package com.example.registration.view.signupScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.constants.InputsRegex
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.LightGray
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.view.utils.CameraPreview
import com.example.registration.viewModels.SignupViewModel
import com.example.registration.viewModels.TextFieldType
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel,
    navController: NavController,
) {
    val activity = LocalContext.current as Activity
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    //sheet
    var isDatePickerSheetOpen by remember {
        mutableStateOf(false)
    }
    var isProfileSheetOpen by remember {
        mutableStateOf(false)
    }
    var isCameraSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isPhotoTaken by remember {
        mutableStateOf(false)
    }

    //state
    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val datePickerState = rememberDatePickerState()
    val cameraSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    val takenPicturesSheetState = rememberModalBottomSheetState()
    val showTakenPhotoSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // camera essentials

    val selectedImageType by signupViewModel.selectedImageType.collectAsState()

    var isProfileSelected by remember {
        mutableStateOf(false)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                signupViewModel.updateSelectedImagType(idx = 1)
                signupViewModel.updateSelectedImage(uri = uri)
                isProfileSelected = true
            }
        }
    )

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                androidx.camera.view.CameraController.IMAGE_CAPTURE
            )

        }
    }
    val selectedImage by signupViewModel.selectedImage.collectAsState()


    // UI state
    val signupData = signupViewModel.signupData.collectAsState() //Test
    val capturedImage by signupViewModel.capturedImage.collectAsState()


    val focusManager = LocalFocusManager.current

    val keyBoardState by keyboardAsState()

    //permission
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

    val addressColor by remember {
        mutableStateOf(false)
    }
    var passwordColor by remember {
        mutableStateOf(false)
    }
    var confirmPasswordColor by remember {
        mutableStateOf(false)
    }

    var isAgeFocused by remember {
        mutableStateOf(false)
    }


    var confirmPassword by remember {
        mutableStateOf("")
    }

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


    val cameraContract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                isCameraSheetOpen = true

            } else {
//                if (!hasRequiredPermission(mContext = context, PERMISSIONS = PERMISSIONS)) {
//                    ActivityCompat.requestPermissions(
//                        activity,
//                        PERMISSIONS,
//                        0
//                    )
//                } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_SHORT).show()
//                }
            }
        }


    //list
    val emailList = signupViewModel.emailList
    val phoneList = signupViewModel.phoneList

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    // bring intoView View Requester
    val namesBringIntoView = remember {
        BringIntoViewRequester()
    }
    val emailBringIntoView = remember {

        BringIntoViewRequester()
    }
    val phoneBringIntoView = remember {
        BringIntoViewRequester()
    }
    val passwordBringIntoView = remember {
        BringIntoViewRequester()
    }
    val confirmPasswordBringIntoView = remember {

        BringIntoViewRequester()
    }


    //focus requesters
    val fNameFocusRequester = remember {
        FocusRequester()
    }
    val lNameFocusRequester = remember {
        FocusRequester()
    }
    val ageFocusRequester = remember {
        FocusRequester()
    }
    val emailFocusRequester = remember {
        FocusRequester()
    }
    val phoneFocusRequester = remember {
        FocusRequester()
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val confirmPasswordFocusRequester = remember {
        FocusRequester()
    }



    BackHandler {
        navController.navigateUp()
    }

    Column(
        modifier = Modifier
            .background(LightGray)
            .verticalScroll(scrollState)
            .padding(horizontal = MaterialTheme.dimens.signupDimension.pageHorizontalPadding16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        UserProfile(
            chooseProfileImage = {
                isProfileSheetOpen = true

            },
            selectedImageUri = selectedImage,
            selectedImageType = selectedImageType,
            selectedCameraImage = capturedImage,
            isProfileSelected = isProfileSelected,
            openCamera = {

                cameraContract.launch(
                    Manifest.permission.CAMERA
                )

                if (hasRequiredPermission(
                        mContext = context,
                        PERMISSIONS = PERMISSIONS
                    )
                ) {
                    isCameraSheetOpen = true

                }

            },
            openGallery = {

                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )

            },
            removeProfile = {
                isProfileSelected = false
                signupViewModel.updateSelectedImagType(idx = 0)

            }

        )


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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.signupDimension.padding08,
                        vertical = MaterialTheme.dimens.signupDimension.padding08
                    )
            ) {

                CustomOutlinedInput(
                    modifier = Modifier
                        .bringIntoViewRequester(namesBringIntoView)
                        .focusRequester(focusRequester = fNameFocusRequester),
                    text = signupData.value.firstName,
                    onTextChanged = {

                        signupViewModel.updateSignupData(it, TextFieldType.FirstName)

                    },
                    label = "First name",
                    isError = fNameColor,
                    regex = InputsRegex.NAME_REGEX,
                )


                CustomOutlinedInput(
                    modifier = Modifier
                        .focusRequester(focusRequester = lNameFocusRequester),
                    text = signupData.value.lastName,
                    onTextChanged = {

                        signupViewModel.updateSignupData(it, TextFieldType.LastName)
                    },
                    label = "Last name",
                    isError = lNameColor,
                    regex = InputsRegex.NAME_REGEX

                )
            }
        }
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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.signupDimension.padding08,
                        vertical = MaterialTheme.dimens.signupDimension.padding08
                    )
            ) {


                SignupEmail(
                    modifier = Modifier
                        .bringIntoViewRequester(emailBringIntoView),
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
                    isFieldError = signupViewModel.emailListColor,
                    removeField = {
                        if (emailList.size > 1 && it != primaryEmailIndex) {

                            if (primaryEmailIndex == 1) {
                                emailList.removeAt(0)
                                signupViewModel.emailListColor.removeAt(0)
                                primaryEmailIndex = 0
                            } else {

                                emailList.removeAt(it)
                                signupViewModel.emailListColor.removeAt(it)

                            }

                        }

                    },
                    regex = InputsRegex.EMAIL_ALLOWED_REGEX,
                    emailFocusRequester = emailFocusRequester,


                    )
            }


        }

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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.signupDimension.padding08,
                        vertical = MaterialTheme.dimens.signupDimension.padding08
                    )
            ) {

                SignupPhone(
                    modifier = Modifier
                        .bringIntoViewRequester(phoneBringIntoView)
                        .focusRequester(focusRequester = phoneFocusRequester),
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
                    isFieldError = signupViewModel.phoneListColor,
                    removeField = {
                        if (phoneList.size > 1 && it != primaryPhoneIndex) {
                            if (primaryPhoneIndex == 1) {
                                phoneList.removeAt(0)
                                signupViewModel.phoneListColor.removeAt(0)
                                primaryPhoneIndex = 0
                            } else {

                                phoneList.removeAt(it)
                                signupViewModel.phoneListColor.removeAt(it)
                            }

                        }
                    },
                    regex = InputsRegex.PHONE_NUMBER_REGEX,
                    phoneFocusRequester = phoneFocusRequester


                )
            }


        }



        LaunchedEffect(key1 = keyBoardState) {
            if (keyBoardState == Keyboard.Closed && isAgeFocused) {
                focusManager.clearFocus()

                if (signupData.value.age != null && signupData.value.age.isNotEmpty()) {
                    signupViewModel.updateSignupData(
                        text = convertMillisToDate(
                            Date().time.minus(
                                yearsToMillis(signupData.value.age.toLong())
                            )
                        ),
                        TextFieldType.DOB,
                    )
                } else {
                    signupViewModel.updateSignupData("0", TextFieldType.Age)
                }

            }
        }

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


                CustomOutlinedInput(
                    modifier = Modifier
                        .weight(0.4f)
                        .focusRequester(focusRequester = ageFocusRequester),
                    text = if (signupData.value.age != null) signupData.value.age else "0",
                    onTextChanged = {

                        signupViewModel.updateSignupData(it, TextFieldType.Age)
                    },
                    label = "Age",
                    keyBoardType = KeyboardType.Phone,
                    focusChanged = { state ->
                        isAgeFocused = state.isFocused
                    },
                    regex = InputsRegex.AGE_REGEX,
                    updateFocusChangeValue = {
                        if (signupData.value.age != null && signupData.value.age.isNotEmpty()) {
                            signupViewModel.updateSignupData(
                                text = convertMillisToDate(
                                    Date().time.minus(
                                        yearsToMillis(signupData.value.age.toLong())
                                    )
                                ),
                                TextFieldType.DOB,
                            )
                        } else {
                            signupViewModel.updateSignupData("0", TextFieldType.Age)
                        }
                    }

                )
                Divider(
                    color = Color.Black.copy(alpha = 0.3f),
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )


                DatePickerBar(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .wrapContentSize()
                        .weight(0.4f),
                    text = "Pick your date of birth",
                    onClick = { isDatePickerSheetOpen = true },
                    selectedDate = signupData.value.dob,
                )
            }


        }


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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.signupDimension.padding08,
                        vertical = MaterialTheme.dimens.signupDimension.padding08
                    )
            ) {

                CustomOutlinedInput(
                    text = signupData.value.address,
                    onTextChanged = {

                        signupViewModel.updateSignupData(text = it, TextFieldType.Address)
                    },
                    label = "Enter your address",
                    minLines = 3,
                    maxLines = 5,
                    isError = addressColor,
                    regex = InputsRegex.ALLOW_ANY_REGEX
                )
            }

        }


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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.signupDimension.padding08,
                        vertical = MaterialTheme.dimens.signupDimension.padding08
                    )
            ) {


                CustomOutlinedPasswordInput(
                    modifier = Modifier
                        .focusRequester(focusRequester = passwordFocusRequester),
                    text = signupData.value.password,
                    onTextChanged = {
                        signupViewModel.updateSignupData(
                            text = it,
                            TextFieldType.Password
                        )
                    },
                    label = "Password",
                    isError = passwordColor,
                    regex = InputsRegex.PASSWORD_REGEX


                )

                CustomOutlinedPasswordInput(
                    modifier = Modifier
                        .focusRequester(focusRequester = confirmPasswordFocusRequester),
                    text = confirmPassword,
                    onTextChanged = { confirmPassword = it },
                    label = "confirm password",
                    isError = confirmPasswordColor,
                    regex = InputsRegex.PASSWORD_REGEX


                )
            }


        }


        Button(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                    horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
                )
                .fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Blue
            ),
            onClick = {

                signupViewModel.emailListColor[primaryEmailIndex] =
                    emailList[primaryEmailIndex].isEmpty()
                signupViewModel.phoneListColor[primaryPhoneIndex] =
                    phoneList[primaryPhoneIndex].isEmpty()

                fNameColor = signupData.value.firstName.isEmpty()
                lNameColor = signupData.value.lastName.isEmpty()
                passwordColor = signupData.value.password.isEmpty()
                confirmPasswordColor = confirmPassword.isEmpty()


                if (
                    signupViewModel.checkFieldsValue(
                        primaryEmail = emailList[primaryEmailIndex],
                        primaryPhone = phoneList[primaryPhoneIndex],
                        firstName = signupData.value.firstName,
                        lastName = signupData.value.lastName,
                        password = signupData.value.password,
                        confirmPassword = confirmPassword
                    )

                ) {
                    if (signupViewModel.checkPassword(
                            password = signupData.value.password,
                            confirmPassword = confirmPassword
                        )
                    ) {
                        val otherEmails = signupViewModel.convertListToString(
                            list = emailList,
                            idx = primaryEmailIndex
                        )
                        val otherPhones = signupViewModel.convertListToString(
                            list = phoneList,
                            idx = primaryPhoneIndex
                        )

                        signupViewModel.updateSignupData(
                            text = otherEmails,
                            TextFieldType.OtherEmails
                        )
                        signupViewModel.updateSignupData(
                            text = otherPhones,
                            TextFieldType.OtherPhones
                        )
                        signupViewModel.updateSignupData(
                            text = emailList[primaryEmailIndex],
                            TextFieldType.PrimaryEmail
                        )
                        signupViewModel.updateSignupData(
                            text = phoneList[primaryPhoneIndex],
                            TextFieldType.PrimaryPhone
                        )

                        signupViewModel.userDetails = signupViewModel.getSignupDetails()
                        signupViewModel.insertData()
                        Toast.makeText(context, "Signup success", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()

                    } else if (confirmPasswordColor){
                        Toast.makeText(
                            context,
                            "check your password is same",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else{
                        Toast.makeText(
                            context,
                            "check your credentials",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }


                } else {
                    Log.i("passwordColor", passwordColor.toString())
                    val toastText: String


                     if (fNameColor) {
                        coroutineScope.launch {
                            namesBringIntoView.bringIntoView()
                            fNameFocusRequester.requestFocus()
                        }
                        toastText = "Check First name value"

                    } else if (lNameColor) {
                        coroutineScope.launch {
                            lNameFocusRequester.requestFocus()
                        }
                        toastText = "Check last name value"
                    } else if (signupViewModel.emailListColor[primaryEmailIndex]) {
                        coroutineScope.launch {
                            emailBringIntoView.bringIntoView()
                            emailFocusRequester.requestFocus()
                        }
                        toastText = "Check given email is valid"

                    } else if (signupViewModel.phoneListColor[primaryPhoneIndex]) {
                        coroutineScope.launch {
                            phoneBringIntoView.bringIntoView()
                            phoneFocusRequester.requestFocus()
                        }
                        toastText = "Check phone  value"
                    } else if (passwordColor) {
                        coroutineScope.launch {
                            passwordBringIntoView.bringIntoView()
                            passwordFocusRequester.requestFocus()
                        }
                        toastText = "Check password value"
                    } else {
                        coroutineScope.launch {
                            confirmPasswordBringIntoView.bringIntoView()
                            confirmPasswordFocusRequester.requestFocus()
                        }
                        toastText = "Check confirm password value"
                    }

                    val toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
                    if (keyBoardState == Keyboard.Opened) {
                        toast.setGravity(Gravity.CENTER, 0, 0)
                    }
                    toast.show()


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

                            signupViewModel.updateSignupData(it, TextFieldType.DOB)
                        },
                        updateAge = {

                            signupViewModel.updateSignupData(it, TextFieldType.Age)
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
                                    onPhotoTaken = {
//                                        onPhotoTaken = signupViewModel::takePhoto
                                        signupViewModel.updateCapturedImage(bitmap = it)

                                    }

                                )
//
                                isPhotoTaken = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Camera,
                                contentDescription = "take picture"
                            )
                        }

                    }

                }


            }
        }
    }
    Log.i("bitmap", capturedImage.toString())


    if (isPhotoTaken) {
        ModalBottomSheet(
            onDismissRequest = {
                isPhotoTaken = false
            },
            sheetState = showTakenPhotoSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (capturedImage != null) {
                    Image(
                        modifier = Modifier
                            .weight(0.9f)
                            .fillMaxSize()
                            .size(MaterialTheme.dimens.signupDimension.profileSize),
                        bitmap = capturedImage!!.asImageBitmap(),
                        contentDescription = "profile",
                        contentScale = ContentScale.FillBounds
                    )
                    Row(
                        modifier = Modifier
                            .padding(MaterialTheme.dimens.signupDimension.itemHorizontalPadding08)
                            .weight(0.1f)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(onClick = { isPhotoTaken = false }) {
                            Image(
                                painter = painterResource(id = R.drawable.close_camera_ic),
                                contentDescription = "close"
                            )
                        }

                        IconButton(onClick = {

                            isPhotoTaken = false
                            isProfileSelected = true
                            isCameraSheetOpen = false
                            signupViewModel.updateSelectedImagType(idx = 2)

                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.tick_ic),
                                contentDescription = "close"
                            )
                        }

                    }
                }
            }


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

fun Toast.showAboveKeyboard(containerView: View) {

    val insets = ViewCompat.getRootWindowInsets(containerView)
    val imeVisible = insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
    val imeHeight = insets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
    val fallbackYOffset =
        containerView.resources.getDimensionPixelOffset(androidx.appcompat.R.dimen.abc_action_bar_default_height_material)
    val noSoftKeyboardYOffset =
        containerView.resources.getDimensionPixelOffset(androidx.appcompat.R.dimen.abc_action_button_min_width_material)
    setGravity(
        Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,
        0,
        if (imeVisible) imeHeight ?: fallbackYOffset else noSoftKeyboardYOffset
    )
    show()
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