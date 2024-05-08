package com.example.registration.view.contactScreen.allContacts

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.constants.constantModals.ContactBasicDetails
import com.example.registration.ui.theme.dimens


@Composable
fun ContactCard(
    contact: ContactBasicDetails,
    contactDetailsNavigation: (userId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimens.allContactsDimension.padding04,
                vertical = MaterialTheme.dimens.allContactsDimension.padding08
            )
            .clickable {
                contactDetailsNavigation(contact.userId.toString())
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.allContactsDimension.cardElevation10
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactImageLoader(
                modifier = Modifier,
                profileImage = contact.profileImage
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.allContactsDimension.padding02)
                        .fillMaxWidth(),
                    text = contact.fName,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.allContactsDimension.padding02)
                        .fillMaxWidth(),
                    text = contact.lName,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                )
            }

        }
    }

}

@Composable
fun ContactImageLoader(
    profileImage: Bitmap?,
    modifier: Modifier = Modifier
) {
    if (profileImage != null) {
        Box(modifier = modifier){
            AsyncImage(
                modifier = Modifier
                .padding(MaterialTheme.dimens.contactDimension.padding08)
                    .size(50.dp)
                    .clip(CircleShape),
                model = profileImage,
                contentDescription = "profile",
                contentScale = ContentScale.FillBounds,
            )
        }
    } else {
        Image(
            modifier = modifier
                .size(60.dp)
                .padding(MaterialTheme.dimens.contactDimension.padding08),
            painter = painterResource(id = R.drawable.profile_icon_),
            contentDescription = "profile"
        )
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
private fun PrevAllContacts() {
    ContactCard(
        contact = ContactBasicDetails(
            fName = "Raghu",
            lName = "ram",
            userId = 8,
            profileImage = null
        ),
        contactDetailsNavigation = {}
    )
}

