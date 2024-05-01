package com.example.registration.view.contactScreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens

@Composable
fun UserProfileImage(
    modifier: Modifier,
    imageBitmap: Bitmap?,
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
            if (imageBitmap !=null){
                AsyncImage(
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.dimens.signupDimension.pageVerticalPadding08)
                        .clip(RoundedCornerShape(50))
                        .size(MaterialTheme.dimens.signupDimension.profileSize)
                        ,
                    model = imageBitmap,
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                )
            }else{
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
}


@Composable
fun ListOfTextFields(
    modifier: Modifier,
    listOfItems: List<String>,
    itemName: String,
    primaryEmail: String
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
        if (item.isNotEmpty()) OutputBars(text = item)
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
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
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
        text = itemName,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuDropDown(
    modifier: Modifier
) {
    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier,
            onClick = { isDropDownExpanded = !isDropDownExpanded }
        ) {
            Icon(
                modifier = Modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.menu_vertical_ic),
                contentDescription = "menu",
                tint = Blue
            )
        }



    }
    Row(
        modifier = modifier
            .padding(MaterialTheme.dimens.signupDimension.padding16)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .width(100.dp),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = it }
        ) {
            ExposedDropdownMenu(
                modifier = Modifier
                    .width(100.dp)
                    .wrapContentSize(),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },

                ) {
                DropdownMenuItem(
                    modifier = Modifier,
                    text = { Text(text = "Edit") },
                    onClick = {
                        isDropDownExpanded = false
                    },
                    leadingIcon = {
                        Image(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                )
            }
        }
    }
}