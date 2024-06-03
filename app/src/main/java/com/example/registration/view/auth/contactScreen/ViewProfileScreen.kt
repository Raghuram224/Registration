package com.example.registration.view.contactScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.ui.theme.dimens
import com.example.registration.viewModels.ViewProfileScreenViewModel
import androidx.compose.material.MaterialTheme as MaterialTheme1

@Composable
fun ViewProfileScreen(
    navController: NavController,
    viewProfileScreenViewModel: ViewProfileScreenViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .size(50.dp)
                        .padding(MaterialTheme1.dimens.contactDimension.padding08),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.close
                    ),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.Black)
                .fillMaxSize()
        ) {

            if (viewProfileScreenViewModel.userProfileImage != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = viewProfileScreenViewModel.userProfileImage,
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Fit,
                )
            } else {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.profile_icon_),
                    contentDescription = stringResource(
                        id = R.string.profile
                    )
                )
            }

        }
    }

}

