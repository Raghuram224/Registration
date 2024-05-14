package com.example.registration.view.contactScreen.allContacts

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.navigation.Screens
import com.example.registration.ui.theme.White
import com.example.registration.ui.theme.dimens
import com.example.registration.view.contactScreen.ExitPopup
import com.example.registration.viewModels.AllContactsViewModel


@Composable
fun AllContactsScreen(
    modifier: Modifier,
    allContactsViewModel: AllContactsViewModel,
    navController: NavController
) {
    var exitPopUpState by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
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
                    tint = Color.Black
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.contactDimension.padding16)
                .padding(innerPadding),
        ) {
            items(allContactsViewModel.allContacts) { contact ->
                ContactCard(
                    contact = contact,
                    contactDetailsNavigation = { userId ->

                        navController.navigate(
                            Screens.ContactScreens.passArguments(
                                userId = userId,
                                isAdmin = true
                            )
                        )
                    }
                )
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
                },
            )
        }


    }
}
