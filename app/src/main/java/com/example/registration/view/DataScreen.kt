package com.example.registration.view

import android.util.Log
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
    Log.i("Data",signupViewModel.publicSignupDetails.toString())
    if (signupViewModel.publicSignupDetails!=null){
        Column {
            Text(text = "First name: ${signupViewModel.publicSignupDetails.firstName}")
            Text(text = "Last name: ${signupViewModel.publicSignupDetails.lastName}")
            Text(text = "age: ${signupViewModel.publicSignupDetails.age}")
            Text(text = "dob: ${signupViewModel.publicSignupDetails.dob}")
            Text(text = "address: ${signupViewModel.publicSignupDetails.address}")
            Text(text = "other emails: ${signupViewModel.publicSignupDetails.otherEmails}")
            Text(text = "other phones: ${signupViewModel.publicSignupDetails.otherPhones}")

            Text(text = "email: ${signupViewModel.publicSignupDetails.primaryEmail}")
            Text(text = "phone: ${signupViewModel.publicSignupDetails.primaryPhone}")
        }




    }


}