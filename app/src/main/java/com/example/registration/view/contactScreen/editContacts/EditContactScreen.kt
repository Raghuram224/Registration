package com.example.registration.view.contactScreen.editContacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.constants.constantModals.Keyboard
import com.example.registration.constants.constantModals.OtherEmailOrPhoneFields
import com.example.registration.constants.constantModals.TextFieldType
import com.example.registration.constants.InputsRegex
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.view.signupScreen.CustomColumnCardCreator
import com.example.registration.view.signupScreen.CustomDatePicker
import com.example.registration.view.signupScreen.CustomOutlinedInput
import com.example.registration.view.signupScreen.CustomRowCardCreator
import com.example.registration.view.signupScreen.DatePickerBar
import com.example.registration.view.signupScreen.SignupEmail
import com.example.registration.view.signupScreen.SignupPhone
import com.example.registration.view.signupScreen.UserProfile
import com.example.registration.view.signupScreen.convertMillisToDate
import com.example.registration.view.signupScreen.convertUriToBitmapAboveAndroidP
import com.example.registration.view.signupScreen.convertUriToBitmapBelowAndroidP
import com.example.registration.view.signupScreen.keyboardAsState
import com.example.registration.view.signupScreen.takePhoto
import com.example.registration.view.signupScreen.yearsToMillis
import com.example.registration.view.utils.CameraPreview
import com.example.registration.viewModels.EditContactsViewmodel
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState", "StateFlowValueCalledInComposition")
@Composable
fun EditContactScreen(
    modifier: Modifier = Modifier,
    editContactsViewModel: EditContactsViewmodel,
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
                editContactsViewModel.updateProfileImage(bitmap = null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    editContactsViewModel.updateProfileImage(
                        bitmap = convertUriToBitmapAboveAndroidP(
                            uri = uri,
                            activity = activity
                        )
                    )

                } else {
                    editContactsViewModel.updateProfileImage(
                        bitmap = convertUriToBitmapBelowAndroidP(
                            uri = uri,
                            activity = activity
                        )
                    )
                }

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
    val contactData = editContactsViewModel.contactData.collectAsState() //Test
    val profileImage by editContactsViewModel.profileImage.collectAsState()

    val focusManager = LocalFocusManager.current

    val keyBoardState by keyboardAsState()


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

    var isAgeFocused by remember {
        mutableStateOf(false)
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
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_SHORT).show()

            }
        }

    //list
    val emailList = editContactsViewModel.emailList
    val phoneList = editContactsViewModel.phoneList


    val coroutineScope = rememberCoroutineScope()

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

    var tempImageHolder by rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }


//    BackHandler {
//        navController.navigateUp()
//
//    }
    val isUserIdUpdated by editContactsViewModel.isUserIdUpdated.collectAsState()

    if (!isUserIdUpdated) {
        editContactsViewModel.updateUserId(
            userId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("userId")
        )
    }


    Scaffold(
        topBar = {
            EditContactsTopBar(
                modifier = Modifier,
                cancelButtonClick = { navController.navigateUp() },
                saveButtonClick = {

                    editContactsViewModel.emailListColor[primaryEmailIndex] =
                        emailList[primaryEmailIndex].isEmpty()

                    fNameColor = contactData.value.firstName.isEmpty()
                    lNameColor = contactData.value.lastName.isEmpty()



                    if (
                        editContactsViewModel.checkFieldsValue(
                            primaryEmail = emailList[primaryEmailIndex],
                            primaryPhone = phoneList[primaryPhoneIndex],
                            firstName = contactData.value.firstName,
                            lastName = contactData.value.lastName,

                            )

                    ) {

                        val otherEmails = editContactsViewModel.convertListToString(
                            list = emailList,
                            idx = primaryEmailIndex
                        )
                        val otherPhones = editContactsViewModel.convertListToString(
                            list = phoneList,
                            idx = primaryPhoneIndex
                        )

                        editContactsViewModel.updateOtherEmailOrPhone(
                            text = otherEmails,
                            OtherEmailOrPhoneFields.OtherEmail
                        )
                        editContactsViewModel.updateOtherEmailOrPhone(
                            text = otherPhones,
                            OtherEmailOrPhoneFields.OtherPhones
                        )

                        editContactsViewModel.updateContactData(
                            text = emailList[primaryEmailIndex],
                            TextFieldType.PrimaryEmail
                        )
                        editContactsViewModel.updateContactData(
                            text = phoneList[primaryPhoneIndex],
                            TextFieldType.PrimaryPhone
                        )
                        editContactsViewModel.updateProfileImageIntoDb(bitmap = profileImage)

                        editContactsViewModel.updateData()

                        Toast.makeText(
                            context,
                            "Contact saved",
                            Toast.LENGTH_SHORT
                        ).show()

//                        editContactsViewModel.updateUIData()  // Update ui data

                        navController.navigateUp()


                    } else {

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
                        } else if (editContactsViewModel.emailListColor[primaryEmailIndex]) {
                            coroutineScope.launch {
                                emailBringIntoView.bringIntoView()
                                emailFocusRequester.requestFocus()
                            }
                            toastText = "Check given email is valid"

                        } else {

                            toastText = "Check fields value value"
                        }

                        val toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
                        if (keyBoardState == Keyboard.Opened) {
                            toast.setGravity(Gravity.CENTER, 0, 0)
                        }
                        toast.show()

                    }


                }
            )
        }
    ) { innerPadding -> // body
        Column(
            modifier = Modifier
                .background(com.example.registration.ui.theme.LightGray)
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

                    if (editContactsViewModel.checkRequiredPermission()) {
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
                    editContactsViewModel.updateProfileImage(bitmap = null)

                }

            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = @Composable {
                    CustomOutlinedInput(
                        modifier = Modifier
                            .bringIntoViewRequester(namesBringIntoView)
                            .focusRequester(focusRequester = fNameFocusRequester),
                        text = contactData.value.firstName,
                        onTextChanged = {

                            editContactsViewModel.updateContactData(it, TextFieldType.FirstName)

                        },
                        label = "First name",
                        isError = fNameColor,
                        regex = InputsRegex.NAME_REGEX,
                    )


                    CustomOutlinedInput(
                        modifier = Modifier
                            .focusRequester(focusRequester = lNameFocusRequester),
                        text = contactData.value.lastName,
                        onTextChanged = {

                            editContactsViewModel.updateContactData(it, TextFieldType.LastName)
                        },
                        label = "Last name",
                        isError = lNameColor,
                        regex = InputsRegex.NAME_REGEX

                    )
                }
            )


            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = @Composable {
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
                        isFieldError = editContactsViewModel.emailListColor,
                        removeField = {
                            if (emailList.size > 1 && it != primaryEmailIndex) {

                                if (primaryEmailIndex == 1) {
                                    emailList.removeAt(0)
                                    editContactsViewModel.emailListColor.removeAt(0)
                                    primaryEmailIndex = 0
                                } else {

                                    emailList.removeAt(it)
                                    editContactsViewModel.emailListColor.removeAt(it)

                                }

                            }

                        },
                        regex = InputsRegex.EMAIL_ALLOWED_REGEX,
                        emailFocusRequester = emailFocusRequester,


                        )
                }
            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = @Composable {
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
                        removeField = {
                            if (phoneList.size > 1 && it != primaryPhoneIndex) {
                                if (primaryPhoneIndex == 1) {
                                    phoneList.removeAt(0)
                                    editContactsViewModel.phoneListColor.removeAt(0)
                                    primaryPhoneIndex = 0
                                } else {

                                    phoneList.removeAt(it)
                                    editContactsViewModel.phoneListColor.removeAt(it)
                                }

                            }
                        },
                        regex = InputsRegex.PHONE_NUMBER_REGEX,
                        phoneFocusRequester = phoneFocusRequester


                    )
                }
            )


            LaunchedEffect(key1 = keyBoardState) {
                if (keyBoardState == Keyboard.Closed && isAgeFocused) {
                    focusManager.clearFocus()

                    if (contactData.value.age != null && contactData.value.age.isNotEmpty()) {
                        editContactsViewModel.updateContactData(
                            text = convertMillisToDate(
                                Date().time.minus(
                                    yearsToMillis(contactData.value.age.toLong())
                                )
                            ),
                            TextFieldType.DOB,
                        )
                    } else {
                        editContactsViewModel.updateContactData("0", TextFieldType.Age)
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
                        text = if (contactData.value.age != null) contactData.value.age else "0",
                        onTextChanged = {

                            editContactsViewModel.updateContactData(it, TextFieldType.Age)
                        },
                        label = "Age",
                        keyBoardType = KeyboardType.Phone,
                        focusChanged = { state ->
                            isAgeFocused = state.isFocused
                        },
                        regex = InputsRegex.AGE_REGEX,
                        updateFocusChangeValue = {
                            if (contactData.value.age != null && contactData.value.age.isNotEmpty()) {
                                editContactsViewModel.updateContactData(
                                    text = convertMillisToDate(
                                        Date().time.minus(
                                            yearsToMillis(contactData.value.age.toLong())
                                        )
                                    ),
                                    TextFieldType.DOB,
                                )
                            } else {
                                editContactsViewModel.updateContactData("0", TextFieldType.Age)
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
                        selectedDate = contactData.value.dob,
                    )
                }
            )


            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = @Composable {
                    CustomOutlinedInput(
                        text = contactData.value.address,
                        onTextChanged = {

                            editContactsViewModel.updateContactData(
                                text = it,
                                TextFieldType.Address
                            )
                        },
                        label = "Enter your address",
                        minLines = 3,
                        maxLines = 5,
                        isError = addressColor,
                        regex = InputsRegex.ALLOW_ANY_REGEX
                    )
                }
            )
            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    CustomOutlinedInput(
                        text = contactData.value.website,
                        onTextChanged = {
                            editContactsViewModel.updateContactData(
                                text = it,
                                type = TextFieldType.Website
                            )
                        },
                        label = "Website",
                        regex = InputsRegex.WEBSITE_REGEX_ALLOWED_PARAM
                    )
                }
            )


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

                                editContactsViewModel.updateContactData(it, TextFieldType.DOB)
                            },
                            updateAge = {

                                editContactsViewModel.updateContactData(it, TextFieldType.Age)
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

                        IconButton(onClick = {
                            isPhotoTaken = false
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.close_camera_ic),
                                contentDescription = "close"
                            )
                        }

                        IconButton(
                            onClick = {

                                isPhotoTaken = false
                                isProfileSelected = true
                                isCameraSheetOpen = false
                                editContactsViewModel.updateProfileImage(bitmap = tempImageHolder)

                            }) {
                            Image(
                                painter = painterResource(id = R.drawable.tick_ic),
                                contentDescription = "accepted"
                            )
                        }

                    }
                }
            }


        }
    }


}


@Composable
fun EditContactsTopBar(
    modifier: Modifier,
    cancelButtonClick: () -> Unit,
    saveButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            modifier = Modifier
                .weight(0.5f)
                .padding(MaterialTheme.dimens.signupDimension.padding08)
                .fillMaxWidth(),
            onClick = {
                cancelButtonClick()
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Cancel",
                color = Blue,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.Start
                )
            )
        }



        TextButton(
            modifier = Modifier
                .weight(0.5f)
                .padding(MaterialTheme.dimens.signupDimension.padding08)
                .fillMaxWidth(),
            onClick = {
                saveButtonClick()
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Save",
                color = Blue,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.End
                )
            )
        }
    }

}