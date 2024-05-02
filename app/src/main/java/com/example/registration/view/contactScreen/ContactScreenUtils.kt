package com.example.registration.view.contactScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.viewModels.PersonalInformation
import com.example.registration.viewModels.ContactViewModel
import kotlinx.coroutines.delay

@Composable
fun ContactDetails(
    modifier: Modifier,
    uiColor: Color,
    contactDetails: PersonalInformation,
    contactViewModel: ContactViewModel,
    context: Context
) {
    var hasPhonePermission by remember {
        mutableStateOf(false)
    }
    val phoneContract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                hasPhonePermission = true

            } else {
                Toast.makeText(context, "phone permission denied", Toast.LENGTH_SHORT).show()
            }
        }


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
                tintColor = uiColor,
                swipeAction = {
                    if (contactViewModel.hasPhonePermission() || hasPhonePermission) {
                        openDialer(context = context, it)
                    } else {
                        phoneContract.launch(Manifest.permission.CALL_PHONE)
                    }
                },
                context = context,

                )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.Email,
                categoryName = "Email",
                itemValue = contactDetails.primaryEmail,
                itemList = contactDetails.otherEmails,
                tintColor = uiColor,
                context = context

            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.CalendarMonth,
                categoryName = "Birthday",
                itemValue = contactDetails.dob,
                tintColor = uiColor,
                context = context

            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.LocationOn,
                categoryName = "Address",
                itemValue = contactDetails.address,
                tintColor = uiColor,
                context = context

            )
            ContactItem(
                modifier = Modifier,
                icon = Icons.Default.Web,
                categoryName = "Website",
                itemValue = contactDetails.website,
                tintColor = uiColor,
                context = context

            )

        }
    }

}

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.setData(Uri.parse("tel:$phoneNumber"))
    context.startActivity(intent)
}

@Composable
fun ContactProfile(
    modifier: Modifier,
    contactDetails: PersonalInformation
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
    tintColor: Color,
    context: Context,
    swipeAction: (String) -> Unit = {},

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

            if (categoryName == "Number") {
                SwipeToCallContainer(
                    modifier = Modifier,
                    item = itemValue,
                    onCall = { swipeAction(itemValue) }
                ) {
                    Text(
                        modifier = Modifier
                            .heightIn(40.dp)
                            .padding(MaterialTheme.dimens.contactDimension.padding02)
                            .clickable {
                                openDialer(context = context, itemValue)
                            },
                        text = itemValue,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.W300
                        )
                    )
                }
            } else {
                Text(
                    modifier = Modifier
                        .heightIn(40.dp)
                        .padding(MaterialTheme.dimens.contactDimension.padding02),
                    text = itemValue,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.W300
                    )
                )
            }
            if (!itemList.isNullOrEmpty()) {
                if (isExpanded) {

                    itemList.forEach {
                        Text(
                            modifier = Modifier
                                .heightIn(40.dp)
                                .padding(MaterialTheme.dimens.contactDimension.padding02),
                            text = it,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.h6.fontSize,
                                fontWeight = FontWeight.W300
                            )
                        )
                    }
                }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToCallContainer(
    modifier: Modifier,
    item: T,
    onCall: () -> Unit,
    animationDuration: Long = 500,
    content: @Composable (T) -> Unit
) {
    var isCallStarts by remember {
        mutableStateOf(false)
    }

    val state = rememberDismissState(
        confirmValueChange = { value ->
            when (value) {
                DismissValue.DismissedToEnd -> {
                   isCallStarts = true
                    true
                }

                DismissValue.DismissedToStart -> true
                else -> false
            }

        }
    )

    val bgColor =
        when (state.dismissDirection) {
            DismissDirection.StartToEnd -> DarkGreen
            DismissDirection.EndToStart -> Color.Transparent
            else -> Color.Transparent
        }

    LaunchedEffect(key1 = isCallStarts) {
        if (isCallStarts) {
            delay(animationDuration)
            onCall()
            state.reset()
            isCallStarts = false

        }
    }
    SwipeToDismiss(
        state = state,
        background = {
            CallerBackground(
                bgColor = bgColor
            )
        },
        dismissContent = { content(item) },
        directions = setOf(DismissDirection.StartToEnd)
    )


}


@Composable
fun CallerBackground(
    bgColor: Color
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.CenterStart

    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Call",
            tint = White
        )
    }
}

