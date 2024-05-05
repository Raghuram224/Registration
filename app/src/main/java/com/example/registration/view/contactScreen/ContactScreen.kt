package com.example.registration.view.contactScreen

import android.app.Activity
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
import com.example.registration.constants.Screens
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

    val isUserIdUpdated by contactViewModel.isUserIdUpdated.collectAsState()
    if (!isUserIdUpdated) {
        contactViewModel.updateUserDetails(
            userId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("userId"),
            isAdmin = navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("isAdmin")
        )
    }


    Scaffold(
        floatingActionButton = {
            if (!contactViewModel.isAdmin){
                FloatingActionButton(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("userId",contactViewModel.currentUserId)
                        navController.navigate(Screens.EditContactScreens.route)

                    },
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
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(uiColor)
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
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

//                Box(
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(RoundedCornerShape(50))
//                        .background(Color.Black)
//                        .zIndex(1f)
//                        .fillMaxWidth(),
//                    contentAlignment = Alignment.TopCenter
//                ) {
//                    Image(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .fillMaxWidth()
//                            .size(50.dp)
//                            .padding(vertical = MaterialTheme.dimens.signupDimension.itemVerticalPadding08)
//                            .size(MaterialTheme.dimens.signupDimension.profileSize),
//                        painter = painterResource(id = R.drawable.add_ic),
//                        contentDescription = "profile",
//                    )
//                }


