package com.example.registration.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.registration.viewModels.SignupViewModel

@Composable
fun DataScreen (
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel
) {

    if (signupViewModel.signupDetails!=null){
        Column {
            Text(text = "First name: ${signupViewModel.signupDetails.firstName}")
            Text(text = "Last name: ${signupViewModel.signupDetails.lastName}")
            Text(text = "age: ${signupViewModel.signupDetails.age}")
            Text(text = "dob: ${signupViewModel.signupDetails.dob}")
            Text(text = "address: ${signupViewModel.signupDetails.address}")
            Text(text = "other emails: ${signupViewModel.signupDetails.otherEmails}")
            Text(text = "other phones: ${signupViewModel.signupDetails.otherPhones}")

            Text(text = "email: ${signupViewModel.signupDetails.primaryEmail}")
            Text(text = "phone: ${signupViewModel.signupDetails.primaryPhone}")
        }




    }


}