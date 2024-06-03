package com.example.registration.view.contactScreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.registration.R
import com.example.registration.navigation.Screens
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.viewModels.ContactViewModel

@Composable
fun ContactScreen(
    navController: NavController,
    contactViewModel: ContactViewModel,

    ) {

    val activity = LocalContext.current as Activity
    val uiColor = contactViewModel.uiColor

    val userDetails by contactViewModel.contactDetails.collectAsStateWithLifecycle(
        initialValue = null
    )
    val context = LocalContext.current
    var exitPopUpState by remember {
        mutableStateOf(false)
    }



    Scaffold(
        topBar = {
            contactViewModel.isAdmin?.let { isAdmin ->
                if (!isAdmin) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    exitPopUpState = true
                                }
                                .size(50.dp)
                                .padding(MaterialTheme.dimens.contactDimension.padding08),
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(
                                id = R.string.logout
                            ),
                            tint = White
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            contactViewModel.isAdmin?.let { isAdmin ->
                if (!isAdmin) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(
                                Screens.SignupScreens.passArgumentsSignup(
                                    userId = contactViewModel.currentUserId
                                )
                            )

                        },
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 10.dp
                        ),
                        containerColor = White
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            tint = uiColor
                        )
                    }
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


                if (userDetails != null) {
                    ContactProfile(
                        modifier = Modifier
                            .weight(0.3f),
                        contactDetails = userDetails,
                        viewProfileImage = {
                            navController.navigate(
                                Screens.ViewProfileScreen.passArgument(
                                    contactViewModel.currentUserId.toString()
                                )
                            )
                        }
                    )

                    ContactDetails(
                        modifier = Modifier
                            .weight(0.7f),
                        uiColor = uiColor,
                        contactDetails = userDetails,
                        context = context

                    )
                }
            }
        }

        if (exitPopUpState) {
            ExitPopup(
                onDisMiss = { exitPopUpState = false },
                onConfirm = {

                    navController.navigate(Screens.LoginScreens.route) {
                        navController.popBackStack()
                    }
                    exitPopUpState = false
                    Toast.makeText(context, R.string.logout_successfully, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

}


