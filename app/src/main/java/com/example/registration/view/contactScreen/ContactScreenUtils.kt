package com.example.registration.view.contactScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.WebAsset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.viewModels.PersonalInformation

@Composable
fun ContactDetails(
    modifier: Modifier,
    uiColor: Color,
    contactDetails: PersonalInformation
) {
//    val tempList = mutableListOf("9898453", "98988989")
//    val tempEmailList = mutableListOf("raghu@gmail.com", "master@gmail.com")
    Card(
        modifier = modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.contactDimension.padding16,
                    vertical = MaterialTheme.dimens.contactDimension.padding08
                )
                .fillMaxSize()


        ) {
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.contactDimension.padding04),
                text = "Contact Details",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

            )

            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.Phone,
                categoryName = "Number",
                itemValue = contactDetails.primaryPhone,
                itemList = contactDetails.otherPhones,
                tintColor = uiColor
            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.Email,
                categoryName = "Email",
                itemValue = contactDetails.primaryEmail,
                itemList = contactDetails.otherEmails,
                tintColor = uiColor
            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.CalendarMonth,
                categoryName = "Birthday",
                itemValue = contactDetails.dob,
                tintColor = uiColor
            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.LocationOn,
                categoryName = "Address",
                itemValue = contactDetails.address,
                tintColor = uiColor
            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.Web,
                categoryName = "Website",
                itemValue = contactDetails.website,
                tintColor = uiColor
            )

        }
    }

}

@Composable
fun ContactProfile(
    modifier: Modifier,
    contactDetails:PersonalInformation
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .size(130.dp)
                .clip(RoundedCornerShape(50))
                .border(3.dp, color = Color.White, shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center


        ) {
            if (contactDetails.profileImage != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.contactDimension.padding08)
                        .size(120.dp)
                        .clip(RoundedCornerShape(50)),


                    model = contactDetails.profileImage,
                    contentDescription = "profile",
                    contentScale = ContentScale.FillBounds,
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(MaterialTheme.dimens.contactDimension.padding08),
                    painter = painterResource(id = R.drawable.profile_icon_),
                    contentDescription = "profile"
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.contactDimension.padding04)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.contactDimension.padding02)
                    .fillMaxWidth(),
                text = contactDetails.firstName,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,

                    )
            )

            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.contactDimension.padding02)
                    .fillMaxWidth(),
                text = contactDetails.lastName,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
//                fontStyle = FontStyle.Italic

                )
            )
        }


    }

}

@Composable
fun ContactItem(
    modifier: Modifier,
    icon: ImageVector,
    categoryName: String,
    itemValue: String,
    itemList: List<String>? = emptyList(),
    tintColor: Color

) {
    var isExpanded by remember {

        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .padding(MaterialTheme.dimens.contactDimension.padding04)
            .fillMaxWidth()
            .animateContentSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {


        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                tint = tintColor
            )
        }


        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.contactDimension.padding04)
                .fillMaxWidth()
                .weight(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.contactDimension.padding02),
                text = categoryName,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Normal
                )
            )

            Text(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.contactDimension.padding02),
                text = itemValue,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            if (!itemList.isNullOrEmpty()) {
                if (isExpanded) {

                    itemList.forEach {
                        Text(
                            modifier = Modifier
                                .padding(MaterialTheme.dimens.contactDimension.padding02),
                            text = it,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }


//                }
            }
        }
        if (!itemList.isNullOrEmpty()) {
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    tint = tintColor,
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = "Drop down"
                )
            }
        }
    }

}

