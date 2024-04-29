package com.example.registration.view.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.registration.constants.InputsRegex
import com.example.registration.ui.theme.LightGray
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.view.signupScreen.CustomOutlinedInput
import com.example.registration.view.signupScreen.DatePickerBar
import com.example.registration.view.signupScreen.convertMillisToDate
import com.example.registration.view.signupScreen.yearsToMillis
import com.example.registration.viewModels.TextFieldType
import java.util.Date

@Composable
fun ProfileScreen(

) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val listOfEmail: List<String> = mutableListOf("email@gmail.com", "hello@gmail.com")
    val listOfPhones: List<String> = mutableListOf("99909009", "89992898")
    var fName = "Raghu"
    var lName = "Ram"

    Column(
        modifier = Modifier
            .background(LightGray)
            .verticalScroll(scrollState)
            .padding(horizontal = MaterialTheme.dimens.signupDimension.pageHorizontalPadding16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        UserProfileImage(
            modifier = Modifier
                .fillMaxWidth()

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
            UserName(
                modifier = Modifier,
                firstName = fName,
                lastName = lName,
                itemName = "Name",
            )
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

            ListOfTextFields(
                modifier = Modifier
                    .fillMaxWidth(),
                listOfItems = listOfEmail,
                primaryEmail = "raghuram@gmail.com",
                itemName = "email"
            )
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

            ListOfTextFields(
                modifier = Modifier
                    .fillMaxWidth(),
                listOfItems = listOfPhones,
                primaryEmail = "000898989",
                itemName = "Phones"
            )
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
                        .weight(0.4f),
                    text = "20",
                    onTextChanged = {},
                    label = "Age",
                    keyBoardType = KeyboardType.Phone,
                    focusChanged = {},
                    regex = InputsRegex.AGE_REGEX,
                    updateFocusChangeValue = {},
                    readOnly = true

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
                    selectedDate = "23/11/2003",
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
                    text = "Chennai",
                    onTextChanged = {},
                    label = "Enter your address",
                    minLines = 3,
                    maxLines = 5,
                    regex = InputsRegex.ALLOW_ANY_REGEX,
                    readOnly = true
                )
            }

        }
    }

}


@Preview(showSystemUi = true)
@Composable
private fun PreviewUtils() {
    ProfileScreen()
}