package com.example.registration.view.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.registration.R
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.ui.theme.titleStyle

@Composable
fun UserProfileImage(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
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
                    .size(MaterialTheme.dimens.signupDimension.profileSize),
                painter = painterResource(id = R.drawable.add_ic),
                contentDescription = "profile",
            )
        }
    }
}


@Composable
fun ListOfTextFields(
    modifier: Modifier,
    listOfItems: List<String>,
    itemName: String,
    primaryEmail: String
) {

    Column(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.dimens.signupDimension.padding08,
                vertical = MaterialTheme.dimens.signupDimension.padding08
            )
    ) {
        TextHeading(
            modifier = Modifier,
            itemName = itemName
        )
        OutputBars(
            text = primaryEmail,
            color = Blue
        )

        listOfItems.forEachIndexed { idx, item ->
            OutputBars(text = item)
        }
    }


}

@Composable
fun OutputBars(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black.copy(alpha = 0.5f)
) {

    TextField(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding16
            )
            .fillMaxWidth(),
        value = text,
        readOnly = true,
        onValueChange = {},
        colors = TextFieldDefaults.textFieldColors(
            textColor = color,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Blue,
            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.1f),
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Blue

        ),
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.h6.fontSize
        )


    )


}

@Composable
fun TextHeading(
    modifier: Modifier,
    itemName: String
) {
    Text(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08,
                horizontal = MaterialTheme.dimens.signupDimension.itemHorizontalPadding04
            ),
        text = "Primary $itemName",
        style = TextStyle(
            fontSize = MaterialTheme.dimens.signupDimension.headingFont20,
            fontWeight = FontWeight.Medium,
            color = Blue
        )
    )

}

@Composable
fun UserName(
    firstName: String,
    lastName: String,
    itemName: String,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.dimens.signupDimension.padding08,
                vertical = MaterialTheme.dimens.signupDimension.padding08
            )
    ) {
        TextHeading(
            modifier = Modifier,
            itemName = itemName
        )
        OutputBars(text = firstName)
        OutputBars(text = lastName)
    }

}

