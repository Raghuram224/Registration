package com.example.registration.view.contactScreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.registration.constants.InputsRegex
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.dimens
import com.example.registration.view.signupScreen.CustomColumnCardCreator
import com.example.registration.view.signupScreen.CustomOutlinedInput
import com.example.registration.view.signupScreen.CustomRowCardCreator
import com.example.registration.view.signupScreen.DatePickerBar
import com.example.registration.viewModels.PersonalInformation
import com.example.registration.viewModels.ProfileViewModel

@Composable
fun EditContactScreen(
    contactDetails:PersonalInformation
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .background(LightGray)
            .verticalScroll(scrollState)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuDropDown(
            modifier = Modifier
        )

        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.signupDimension.pageHorizontalPadding16),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            UserProfileImage(
                modifier = Modifier
                    .fillMaxWidth(),
                imageBitmap = contactDetails.profileImage

            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = MaterialTheme.dimens.signupDimension.padding08,
                        horizontal = MaterialTheme.dimens.signupDimension.padding04
                    ),
                text = "Personal information",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Blue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    UserName(
                        modifier = Modifier,
                        firstName = contactDetails.firstName,
                        lastName = contactDetails.lastName,
                        itemName = "User name",
                    )
                }
            )


            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    ListOfTextFields(
                        modifier = Modifier
                            .fillMaxWidth(),
//                        listOfItems = contactDetails.otherEmails,
                        listOfItems = emptyList<String>(), //Todo,
                        primaryEmail = contactDetails.primaryEmail,
                        itemName = "Primary email"
                    )
                }
            )



            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    ListOfTextFields(
                        modifier = Modifier
                            .fillMaxWidth(),
//                        listOfItems = profileViewModel.userDetails.otherPhones, Todo
                        listOfItems = emptyList(),
                        primaryEmail = contactDetails.primaryPhone,
                        itemName = "Primary phone"
                    )

                }
            )

            CustomRowCardCreator(
                modifier = Modifier,
                anyComposable = {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextHeading(
                            modifier = Modifier,
                            itemName = "Age and Birth info"
                        )
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
                                    .weight(0.4f),
                                text = contactDetails.age + " yrs",
                                onTextChanged = {},
                                label = "Age",
                                keyBoardType = KeyboardType.Phone,
                                focusChanged = {},
                                regex = InputsRegex.AGE_REGEX,
                                updateFocusChangeValue = {},
                                readOnly = true,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent

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
                                text = "Your date of birth",
                                onClick = { },
                                selectedDate = if (contactDetails.dob.isNotEmpty()) {
//                                    profileViewModel.userDetails.dob
                                    contactDetails.dob
                                } else {
                                    "Unspecified"
                                },
                            )
                        }
                    }


                }
            )

            CustomColumnCardCreator(
                modifier = Modifier,
                anyComposable = {
                    TextHeading(
                        modifier = Modifier,
                        itemName = "Address"
                    )

                    CustomOutlinedInput(
                        text = contactDetails.address,
                        onTextChanged = {},
                        label = "Enter your address",
                        minLines = 3,
                        maxLines = 5,
                        regex = InputsRegex.ALLOW_ANY_REGEX,
                        readOnly = true,
                        textColor = Color.Black,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                }
            )

        }
    }


}

