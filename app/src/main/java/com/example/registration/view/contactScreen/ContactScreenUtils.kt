package com.example.registration.view.contactScreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.constants.InputsRegex
import com.example.registration.constants.constantModals.PersonalInformation
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.ui.theme.LightGray
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import kotlinx.coroutines.delay

@Composable
fun ContactDetails(
    modifier: Modifier,
    uiColor: Color,
    contactDetails: PersonalInformation?,
    context: Context
) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current


    if (contactDetails != null) {
        Card(
            modifier = modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = LightGray
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
                    .verticalScroll(scrollState)
                    .fillMaxSize()


            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.contactDimension.padding04),
                    text = "Contact Details",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = uiColor
                    )

                )

                if (contactDetails.primaryPhone.isNotEmpty()) {
                    CustomCardCreator(
                        modifier = Modifier,
                        anyComposable = {
                            ContactItem(
                                modifier = Modifier,
                                icon = Icons.Default.Phone,
                                categoryName = stringResource(id = R.string.number),
                                itemValue = contactDetails.primaryPhone,
                                itemList = contactDetails.otherPhones,
                                tintColor = uiColor,
                                intentAction = {
                                    openDialer(context = context, it)
                                },
                            )

                        }
                    )
                }
                if (contactDetails.primaryEmail.isNotEmpty()) {
                    CustomCardCreator(
                        modifier = Modifier,
                        anyComposable = {
                            ContactItem(
                                modifier = Modifier,
                                icon = Icons.Default.Email,
                                categoryName = stringResource(id = R.string.email),
                                itemValue = contactDetails.primaryEmail,
                                itemList = contactDetails.otherEmails,
                                tintColor = uiColor,
                                intentAction = { email ->
                                    launchEmailIntent(
                                        context = context,
                                        emailId = email,
                                        subject = "This is subject",
                                        body = "This is body"
                                    )
                                }
                            )

                        }
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.this_contact_is_empty),
                        style = TextStyle(
                            color = uiColor,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                if (contactDetails.dob.isNotEmpty()) {
                    CustomCardCreator(
                        modifier = Modifier,
                        anyComposable = {

                            ContactItem(
                                modifier = Modifier,
                                icon = Icons.Default.CalendarMonth,
                                categoryName = stringResource(id = R.string.birthday),
                                itemValue = contactDetails.dob,
                                tintColor = uiColor,
                            )

                        }
                    )
                }

                if (contactDetails.address.isNotEmpty()) {
                    CustomCardCreator(
                        modifier = Modifier,
                        anyComposable = {


                            ContactItem(
                                modifier = Modifier,
                                icon = Icons.Default.LocationOn,
                                categoryName = stringResource(id = R.string.address),
                                itemValue = contactDetails.address,
                                tintColor = uiColor,
                                intentAction = { address ->
                                    launchMapsIntent(context = context, address = address)
                                }
                            )

                        }
                    )
                }
                if (contactDetails.website.isNotEmpty()) {
                    CustomCardCreator(
                        modifier = Modifier,
                        anyComposable = {

                            ContactItem(
                                modifier = Modifier,
                                icon = Icons.Default.Web,
                                categoryName = stringResource(id = R.string.website),
                                itemValue = contactDetails.website,
                                tintColor = uiColor,
                                intentAction = { website ->
                                    val url =
                                        if (website.matches(regex = Regex(InputsRegex.WEBSITE_REGEX))) website else "$website.com"
                                    uriHandler.openUri("https://$url")
                                }

                            )

                        }
                    )
                }

            }
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
    contactDetails: PersonalInformation?,
    viewProfileImage: () -> Unit,
) {
    if (contactDetails != null) {
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
                ProfileImageLoader(
                    profileImage = contactDetails.profileImage,
                    viewProfileImage = viewProfileImage
                )

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

}

@Composable
fun ContactItem(
    modifier: Modifier,
    icon: ImageVector,
    categoryName: String,
    itemValue: String,
    itemList: List<String>? = emptyList(),
    tintColor: Color,
    intentAction: (String) -> Unit = {},


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
                contentDescription = stringResource(id = R.string.icon),
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
                    fontWeight = FontWeight.Normal,
                    color = tintColor
                )
            )

            if (categoryName == stringResource(id = R.string.number) && itemValue.isNotEmpty()) {
                SwipeToCallContainer(
                    modifier = Modifier,
                    item = itemValue,
                    onCall = { intentAction(itemValue) }
                ) {
                    Text(
                        modifier = Modifier
                            .heightIn(40.dp)
                            .padding(MaterialTheme.dimens.contactDimension.padding02)
                            .clickable {
                                intentAction(itemValue)
                            },
                        text = itemValue,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.W300,
                        )
                    )
                }
            } else {
                Text(
                    modifier = Modifier
                        .heightIn(40.dp)
                        .clickable {
                            intentAction(itemValue)
                        }
                        .padding(MaterialTheme.dimens.contactDimension.padding02),
                    text = itemValue,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.W300,
//                        color = tintColor
                    )
                )
            }
            if (!itemList.isNullOrEmpty()) {
                if (isExpanded) {

                    itemList.forEach { item ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    intentAction(item)
                                }
                                .heightIn(40.dp)
                                .padding(MaterialTheme.dimens.contactDimension.padding02),
                            text = item,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.h6.fontSize,
                                fontWeight = FontWeight.W300,
//                                color = tintColor
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
                    contentDescription = stringResource(id = R.string.drop_down)
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
            .fillMaxWidth(1f)
            .background(bgColor),
        contentAlignment = Alignment.CenterStart

    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = stringResource(id = R.string.call),
            tint = White
        )
    }
}

@Composable
private fun ProfileImageLoader(
    profileImage: Bitmap?,
    viewProfileImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (profileImage != null) {
        AsyncImage(
            modifier = modifier
                .padding(MaterialTheme.dimens.contactDimension.padding08)
                .size(120.dp)
                .clip(RoundedCornerShape(50))
                .clickable {
                    viewProfileImage()
                },
            model = profileImage,
            contentDescription = stringResource(id = R.string.profile),
            contentScale = ContentScale.FillBounds,
        )
    } else {
        Image(
            modifier = modifier
                .size(120.dp)
                .padding(MaterialTheme.dimens.contactDimension.padding08),
            painter = painterResource(id = R.drawable.profile_icon_),
            contentDescription = stringResource(id = R.string.profile)
        )
    }
}

fun launchEmailIntent(context: Context, emailId: String, subject: String, body: String) {
//    val emailIntent = context.packageManager.getLaunchIntentForPackage("com.android.email")
//    intent.putExtra(Intent.EXTRA_TEXT, "body")
//    context.startActivity(Intent.createChooser(intent, "Email"))
//    intent.addCategory(Intent.CATEGORY_APP_EMAIL)

//    intent.setData(Uri.parse("Subject:this is sub"))
    context.startActivity(
//        Intent.createChooser(
        Intent(
            Intent.ACTION_SENDTO,
//        Uri.fromParts("mailto","a@a.com" , null)
        ).apply {
            data = Uri.fromParts("mailto", emailId, null)
            putExtra(Intent.EXTRA_SUBJECT, "sample subject")
        },
//        "Email via..")
    )

}

fun launchMapsIntent(context: Context, address: String) {
    val url = "http://maps.google.com/maps?daddr=$address"

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Composable
fun CustomCardCreator(
    modifier: Modifier,
    anyComposable: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.signupDimension.padding08,
                horizontal = MaterialTheme.dimens.signupDimension.padding04
            ),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {

        anyComposable()

    }
}


@Composable
fun ExitPopup(
    onDisMiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                contentDescription = stringResource(id = R.string.exit),

            )
        },
        title = {
            Text(text = stringResource(id = R.string.logout_the_app))
        },
        text = {
            Text(text = stringResource(id = R.string.do_you_want_to_logout_the_app))
        },
        onDismissRequest = { onDisMiss() },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDisMiss() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }

    )


}