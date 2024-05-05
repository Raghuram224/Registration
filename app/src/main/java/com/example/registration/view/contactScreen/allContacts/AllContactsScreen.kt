package com.example.registration.view.contactScreen.allContacts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.registration.constants.Screens
import com.example.registration.ui.theme.dimens
import com.example.registration.viewModels.AllContactsViewModel


@Composable
fun AllContactsScreen(
    modifier: Modifier,
    allContactsViewModel: AllContactsViewModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.contactDimension.padding16)
    ) {
        items(allContactsViewModel.getAllContacts()){ contact->
            ContactCard(
                contact = contact,
                contactDetailsNavigation = {userId->
                    navController.currentBackStackEntry?.savedStateHandle?.set("userId",userId)
                    navController.currentBackStackEntry?.savedStateHandle?.set("isAdmin",true)
                    navController.navigate(Screens.ContactScreens.route)
                }
            )
        }
    }
}
