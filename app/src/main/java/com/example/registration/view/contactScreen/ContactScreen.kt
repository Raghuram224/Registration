package com.example.registration.view.contactScreen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registration.constants.AllScreens
import com.example.registration.constants.constantModals.Screens
import com.example.registration.ui.theme.White
import com.example.registration.viewModels.ContactViewModel

@Composable
fun ContactScreen(
    navController: NavController,
    contactViewModel: ContactViewModel,

) {

    val activity = LocalContext.current as Activity
    val uiColor = contactViewModel.uiColor

    val userDetails by contactViewModel.userDetails.collectAsState()
    val context = LocalContext.current


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AllScreens.screen.editContactScreen) },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 10.dp
                ),
                containerColor = White
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = uiColor
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(uiColor)
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactProfile(
                    modifier = Modifier
                        .weight(0.3f),
                    contactDetails = userDetails
                )

                ContactDetails(
                    modifier = Modifier
                        .weight(0.7f),
                    uiColor = uiColor,
                    contactDetails = userDetails,
                    contactViewModel = contactViewModel,
                    context = context

                )
            }
        }
    }


}


