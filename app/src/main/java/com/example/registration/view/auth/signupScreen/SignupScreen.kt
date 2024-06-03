package com.example.registration.view.signupScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.constants.InputsRegex
import com.example.registration.constants.constantModals.InputListTypes
import com.example.registration.constants.constantModals.KeyboardStatus
import com.example.registration.constants.constantModals.SignupFieldsColorType
import com.example.registration.constants.constantModals.TextFieldType
import com.example.registration.navigation.Screens
import com.example.registration.ui.theme.LightGray
import com.example.registration.ui.theme.dimens
import com.example.registration.view.utils.CameraPreview
import com.example.registration.viewModels.SignupViewModel
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint(
    "MutableCollectionMutableState", "StateFlowValueCalledInComposition",
    "CoroutineCreationDuringComposition"
)
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
    val showTakenPhotoSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // camera essentials


    var isProfileSelected by remember {
        mutableStateOf(false)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                signupViewModel.updateProfileImage(
                    bitmap = convertUriToBitmapAboveAndroidP(
                        uri = uri,
                        contentResolver = activity.contentResolver
                    )
                )
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


    // UI state
    val userDetails = signupViewModel.userDetails.collectAsState() //Test
    val profileImage by signupViewModel.profileImage.collectAsState()

    val focusManager = LocalFocusManager.current

    val keyBoardState by keyboardAsState()

    val fieldsColor by signupViewModel.fieldsColor.collectAsState()


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
                Toast.makeText(context, R.string.camera_permission_denied, Toast.LENGTH_SHORT)
                    .show()

            }
        }

    val galleryContract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if(it) {
                  photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
            } else {
                Toast.makeText(context, R.string.gallery_permision_denied, Toast.LENGTH_SHORT)
                    .show()

            }
        }


    val coroutineScope = rememberCoroutineScope()

    // bring intoView View Requester
    val namesBringIntoView = remember {
        BringIntoViewRequester()
    }
    val emailBringIntoView = remember {
        BringIntoViewRequester()
    }
    val passwordBringIntoView = remember {
        BringIntoViewRequester()
    }
    val confirmPasswordBringIntoView = remember {
        BringIntoViewRequester()
    }
    val ageBringIntoView = remember {
        BringIntoViewRequester()
    }
    val phoneBringIntoView = remember {
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
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val confirmPasswordFocusRequester = remember {
        FocusRequester()
    }
    val phoneFocusRequester = remember {
        FocusRequester()
    }

    var tempImageHolder by rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }

    //list
    val emailList = signupViewModel.emailList
    val phoneList = signupViewModel.phoneList



    LaunchedEffect(Unit) {

        if (signupViewModel.currentUserId != null) {
            signupViewModel.setContactsDetails()
            confirmPassword = userDetails.value.password
        }
    }


    Scaffold(
        topBar = {
            ContactsTopBar(
                modifier = Modifier,
                cancelButtonClick = {
                    if (signupViewModel.currentUserId != null) {
                        navController.navigateUp()

                    } else {
                        navController.navigate(Screens.LoginScreens.route) {
                            navController.popBackStack()
                        }
                    }
                },
                saveButtonClick = {

                    signupViewModel.updateRequiredFieldsColor(
                        primaryEmailIndex = primaryEmailIndex,
                        confirmPassword = confirmPassword
                    )

                    if (
                        signupViewModel.checkFieldsValue(
                            primaryEmail = emailList[primaryEmailIndex],
                            firstName = userDetails.value.firstName,
                            lastName = userDetails.value.lastName,
                            password = userDetails.value.password,
                            confirmPassword = confirmPassword,
                            primaryPhoneIndex = primaryPhoneIndex
                        )
                    ) {
                        if (signupViewModel.checkPassword(
                                password = userDetails.value.password,
                                confirmPassword = confirmPassword
                            )
                        ) {
                            if (userDetails.value.password.length >= 8) {
                                if (signupViewModel.isUserAgeAndDobMatch()) {
                                    signupViewModel.updateFieldsValuesToUserDetails(
                                        primaryEmailIndex = primaryEmailIndex,
                                        primaryPhoneIndex = primaryPhoneIndex
                                    )

                                    signupViewModel.updateProfileImageIntoUserDetails(bitmap = profileImage)

                                    if (signupViewModel.currentUserId != null) {
                                        createToast(
                                            context = context,
                                            message = R.string.contact_updated,
                                            keyboardStatus = keyBoardState
                                        )

                                        signupViewModel.updateDBData()
                                        navController.popBackStack()
                                    } else {
                                        if (!signupViewModel.isEmailAlreadyTaken(email = emailList[primaryEmailIndex])
                                            && signupViewModel.isUserAgeAndDobMatch()
                                        ) {
                                            createToast(
                                                context,
                                                R.string.signup_success,
                                                keyBoardState
                                            )
                                            signupViewModel.insertData()

                                            navController.popBackStack()

                                        } else {
                                            signupViewModel.emailListColor[primaryEmailIndex] = true

                                            createToast(
                                                context,
                                                R.string.email_is_already_used,
                                                keyBoardState
                                            )

                                            coroutineScope.launch {
                                                emailBringIntoView.bringIntoView()
                                                emailFocusRequester.requestFocus()
                                            }

                                        }

                                    }
                                } else {
                                    createToast(
                                        context,
                                        R.string.your_age_dob_doesnt_match,
                                        keyBoardState
                                    )
                                    coroutineScope.launch {
                                        ageBringIntoView.bringIntoView()
                                        ageFocusRequester.requestFocus()
                                    }
                                }
                            } else {
                                createToast(context, R.string.password_is_too_short, keyBoardState)
                                coroutineScope.launch {
                                    passwordBringIntoView.bringIntoView()
                                    passwordFocusRequester.requestFocus()
                                    signupViewModel.updateFieldsColor(
                                        isError = true,
                                        type = SignupFieldsColorType.Password
                                    )
                                    signupViewModel.updateFieldsColor(
                                        isError = true,
                                        type = SignupFieldsColorType.ConfirmPassword
                                    )

                                }
                            }


                        } else if (fieldsColor.confirmPasswordColor) {
                            createToast(
                                context,
                                R.string.check_your_password_is_same,
                                keyBoardState
                            )
                        } else {
                            createToast(context, R.string.check_your_credentials, keyBoardState)

                        }


                    } else {

                        if (fieldsColor.fNameColor) {
                            coroutineScope.launch {
                                namesBringIntoView.bringIntoView()
                                fNameFocusRequester.requestFocus()
                            }

                            createToast(context, R.string.check_first_name_value, keyBoardState)

                        } else if (fieldsColor.lNameColor) {
                            coroutineScope.launch {
                                lNameFocusRequester.requestFocus()
                            }
                            createToast(context, R.string.check_last_name_value, keyBoardState)
                        } else if (signupViewModel.emailListColor[primaryEmailIndex]) {
                            coroutineScope.launch {
                                emailBringIntoView.bringIntoView()
                                emailFocusRequester.requestFocus()
                            }
                            createToast(context, R.string.check_given_email_is_valid, keyBoardState)

                        } else if (fieldsColor.passwordColor) {
                            coroutineScope.launch {
                                passwordBringIntoView.bringIntoView()
                                passwordFocusRequester.requestFocus()
                            }
                            createToast(context, R.string.check_password_value, keyBoardState)
                        } else if (fieldsColor.confirmPasswordColor) {
                            if (signupViewModel.currentUserId == null) {
                                coroutineScope.launch {
                                    confirmPasswordBringIntoView.bringIntoView()
                                    confirmPasswordFocusRequester.requestFocus()
                                }
                                createToast(
                                    context,
                                    R.string.check_confirm_password_value,
                                    keyBoardState
                                )
                            }
                        } else if (fieldsColor.primaryPhoneColor) {

                            coroutineScope.launch {
                                phoneBringIntoView.bringIntoView()
                                phoneFocusRequester.requestFocus()
                            }

                            createToast(
                                context,
                                R.string.check_primary_phone,
                                keyBoardState
                            )
                        } else {
                            coroutineScope.launch {
                                emailBringIntoView.bringIntoView()
                                emailFocusRequester.requestFocus()
                            }
                            createToast(
                                context,
                                R.string.check_all_input_fields_were_entered_correctly,
                                keyBoardState
                            )
                        }


                    }

                }

            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(LightGray)
                .verticalScroll(scrollState)
                .padding(horizontal = MaterialTheme.dimens.signupDimension.pageHorizontalPadding16)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            UserProfile(
                chooseProfileImage = {
                    isProfileSheetOpen = true

                },
                imageBitmap = profileImage,
                isProfileSelected = isProfileSelected,
                openCamera = {

                    cameraContract.launch(
                        Manifest.permission.CAMERA
                    )

                    if (signupViewModel.checkRequiredCameraPermission()) {
                        isCameraSheetOpen = true

                    }

                },
                openGallery = {

                    requestGalleryPermission(galleryContract)


                },
                removeProfile = {
                    isProfileSelected = false
                    signupViewModel.updateProfileImage(bitmap = null)

                }

            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    CustomOutlinedInput(
                        modifier = Modifier
                            .bringIntoViewRequester(namesBringIntoView)
                            .focusRequester(focusRequester = fNameFocusRequester),
                        text = userDetails.value.firstName,
                        onTextChanged = {

                            signupViewModel.updateUserData(it, TextFieldType.FirstName)

                        },
                        label = stringResource(id = R.string.first_name),
                        isError = fieldsColor.fNameColor,
                        regex = InputsRegex.NAME_REGEX,
                    )


                    CustomOutlinedInput(
                        modifier = Modifier
                            .focusRequester(focusRequester = lNameFocusRequester),
                        text = userDetails.value.lastName,
                        onTextChanged = {

                            signupViewModel.updateUserData(it, TextFieldType.LastName)
                        },
                        label = stringResource(id = R.string.last_name),
                        isError = fieldsColor.lNameColor,
                        regex = InputsRegex.NAME_REGEX

                    )
                }
            )


            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
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
                        removeField = { idx ->

                            if (emailList.size > 1 && idx != primaryEmailIndex) {
                                signupViewModel.removeFieldsOfEmailOrPhoneList(
                                    idx = idx,
                                    InputListTypes.Email
                                )
                                signupViewModel.emailListColor.removeAt(idx)
                                if (primaryEmailIndex != 0 && idx < primaryEmailIndex) primaryEmailIndex--

                            }
                        },
                        regex = InputsRegex.EMAIL_ALLOWED_REGEX,
                        emailFocusRequester = emailFocusRequester,
                        signupViewModel = signupViewModel,


                        )
                }
            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
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
                        removeField = { idx ->

                            if (phoneList.size > 1 && idx != primaryPhoneIndex) {
                                signupViewModel.removeFieldsOfEmailOrPhoneList(
                                    idx = idx,
                                    InputListTypes.Phone
                                )

                                if (primaryPhoneIndex != 0 && idx < primaryPhoneIndex) primaryPhoneIndex--

                            }
                        },
                        regex = InputsRegex.PHONE_NUMBER_REGEX,
                        signupViewModel = signupViewModel,
                        isFieldError = fieldsColor.primaryPhoneColor

                    )
                }
            )


            LaunchedEffect(key1 = keyBoardState) {
                if (keyBoardState == KeyboardStatus.Closed && isAgeFocused) {
                    focusManager.clearFocus()

                    if (userDetails.value.age.isNotEmpty()) {
                        signupViewModel.updateUserData(
                            text = signupViewModel.convertMillisToDate(
                                Date().time.minus(
                                    signupViewModel.yearsToMillis(userDetails.value.age.toLong())
                                )
                            ),
                            TextFieldType.DOB,
                        )
                    } else {
                        signupViewModel.updateUserData("0", TextFieldType.Age)
                    }

                }
            }

            CustomRowCardCreator(
                modifier = Modifier,
                anyComposable = {
                    CustomOutlinedInput(
                        modifier = Modifier
                            .weight(0.4f)
                            .focusRequester(focusRequester = ageFocusRequester),
                        text = userDetails.value.age.ifEmpty { "" },
                        onTextChanged = {
                            signupViewModel.updateUserData(it, TextFieldType.Age)
                        },
                        label = stringResource(id = R.string.age),
                        keyBoardType = KeyboardType.Phone,
                        focusChanged = { state ->
                            isAgeFocused = state.isFocused
                        },
                        regex = InputsRegex.AGE_REGEX,
                        updateFocusChangeValue = {
                            if (userDetails.value.age.isNotEmpty()) {
                                signupViewModel.updateUserData(
                                    text = signupViewModel.convertMillisToDate(
                                        Date().time.minus(
                                            signupViewModel.yearsToMillis(userDetails.value.age.toLong())
                                        )
                                    ),
                                    TextFieldType.DOB,
                                )
                            } else {
                                signupViewModel.updateUserData("", TextFieldType.Age)
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
                        text = stringResource(id = R.string.pick_your_date_of_birth),
                        onClick = { isDatePickerSheetOpen = true },
                        selectedDate = userDetails.value.dob,
                    )
                }
            )


            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    CustomOutlinedInput(
                        text = userDetails.value.address,
                        onTextChanged = {

                            signupViewModel.updateUserData(text = it, TextFieldType.Address)
                        },
                        label = stringResource(id = R.string.enter_your_address),
                        minLines = 3,
                        maxLines = 5,
                        regex = InputsRegex.ALLOW_ANY_REGEX,
                    )
                },

                )
            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    CustomOutlinedInput(
                        text = userDetails.value.website,
                        onTextChanged = {
                            signupViewModel.updateUserData(
                                text = it,
                                type = TextFieldType.Website
                            )
                        },
                        label = stringResource(id = R.string.website),
                        regex = InputsRegex.WEBSITE_REGEX_ALLOWED_PARAM
                    )
                }
            )

            if (signupViewModel.currentUserId == null) {
                CustomColumnCardCreator(
                    modifier = Modifier,
                    anyComposable = {
                        CustomOutlinedPasswordInput(
                            modifier = Modifier
                                .focusRequester(focusRequester = passwordFocusRequester),
                            text = userDetails.value.password,
                            onTextChanged = {
                                signupViewModel.updateUserData(
                                    text = it,
                                    TextFieldType.Password
                                )
                            },
                            label = stringResource(id = R.string.password),
                            isError = fieldsColor.passwordColor,
                            regex = InputsRegex.PASSWORD_REGEX
                        )

                        CustomOutlinedPasswordInput(
                            modifier = Modifier
                                .focusRequester(focusRequester = confirmPasswordFocusRequester),
                            text = confirmPassword,
                            onTextChanged = { confirmPassword = it },
                            label = stringResource(id = R.string.confirm_password),
                            isError = fieldsColor.confirmPasswordColor,
                            regex = InputsRegex.PASSWORD_REGEX


                        )
                    }
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

                                signupViewModel.updateUserData(it, TextFieldType.DOB)
                            },
                            updateAge = {

                                signupViewModel.updateUserData(it, TextFieldType.Age)
                            },
                            signupViewModel = signupViewModel
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
                                            tempImageHolder = it
                                        }
                                    )
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
    }

    Log.i("bitmap", profileImage.toString())


    if (isPhotoTaken) {
        Log.i("bitmap image", tempImageHolder.toString())
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
                if (tempImageHolder != null) {
                    Image(
                        modifier = Modifier
                            .weight(0.9f)
                            .fillMaxSize()
                            .size(MaterialTheme.dimens.signupDimension.profileSize),
                        bitmap = tempImageHolder!!.asImageBitmap(),
                        contentDescription = stringResource(id = R.string.profile),
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

                        IconButton(onClick = {
                            isPhotoTaken = false
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.close_camera_ic),
                                contentDescription = stringResource(id = R.string.close)
                            )
                        }

                        IconButton(onClick = {

                            isPhotoTaken = false
                            isProfileSelected = true
                            isCameraSheetOpen = false
                            signupViewModel.updateProfileImage(bitmap = tempImageHolder)

                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.tick_ic),
                                contentDescription = stringResource(id = R.string.accepted)
                            )
                        }

                    }
                }
            }


        }
    }


}

fun takePhoto(
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

private fun requestGalleryPermission(
    galleryContract: ManagedActivityResultLauncher<String,Boolean>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        galleryContract.launch(
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        galleryContract.launch(
           Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


}

private fun createToast(context: Context, message: Int, keyboardStatus: KeyboardStatus) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
        if (keyboardStatus == KeyboardStatus.Opened) {
            setGravity(Gravity.CENTER, 0, 0)
        }
    }.show()
}

