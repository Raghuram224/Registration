package com.example.registration.view.loginScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.R
import com.example.registration.constants.InputsRegex
import com.example.registration.constants.constantModals.LoginInputFields
import com.example.registration.ui.theme.Blue
import com.example.registration.view.utils.CustomEmail
import com.example.registration.view.utils.CustomHyperLink
import com.example.registration.view.utils.CustomPassword
import com.example.registration.viewModels.LoginViewModel

@Composable
fun LoginPortrait(
    modifier: Modifier = Modifier,
    signupNavigation: () -> Unit,
    successNavigation: (Boolean) -> Unit,
    loginViewModel: LoginViewModel,
    email: String,
    password: String,


    ) {

    val context = LocalContext.current
    var isEmailError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    //Focus requester
    val emailFocusRequester = remember {
        FocusRequester()
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {

        Text(
            text = stringResource(id = R.string.login),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            color = Blue

        )

        Text(
            text = stringResource(id = R.string.login_to_continue_using_the_app),
            color = Color.Black.copy(alpha = 0.5f),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),

            )
        CustomEmail(
            email = email,
            emailStringCallback = {

                Log.i(
                    "inputs match : $it",
                    it.matches(regex = Regex(InputsRegex.EMAIL_ALLOWED_REGEX)).toString()
                )
                if (it.matches(regex = Regex(InputsRegex.EMAIL_ALLOWED_REGEX))) {

                    loginViewModel.updateLoginData(text = it, type = LoginInputFields.Email)
                }
            },
            isEmailError = isEmailError,
            focusRequester = emailFocusRequester
        )
        CustomPassword(
            password = password,
            passwordStringCallback = {
                if (it.matches(regex = Regex(InputsRegex.PASSWORD_REGEX))) {
                    loginViewModel.updateLoginData(
                        text = it,
                        type = LoginInputFields.Password
                    )
                }
            },
            isPasswordError = isPasswordError,
            focusRequester = passwordFocusRequester
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            onClick = {

                if (email.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                    if (email.isNotEmpty() && password.isNotEmpty()) {

                        if (loginViewModel.authenticateUser(email = email, password = password)) {
                            isPasswordError = false
                            createToast(context, R.string.login_success)
                            val userType =
                                loginViewModel.checkUserType(userId = loginViewModel.userId)
                            successNavigation(userType)
                        } else {
                            emailFocusRequester.requestFocus()
                            isEmailError = true
                            isPasswordError = true
                            createToast(context, R.string.email_or_password_wrong)
                        }

                    } else if (email.isEmpty()) {
                        emailFocusRequester.requestFocus()
                        isEmailError = true
                        if (password.isEmpty()) {
                            isPasswordError = true
                        }
                        createToast(context,R.string.check_given_email_is_valid)


                    } else if (password.isEmpty()) {
                        passwordFocusRequester.requestFocus()
                        isPasswordError = true
                        createToast(context,R.string.check_password_value)
                    } else {
                        passwordFocusRequester.requestFocus()
                        isPasswordError = true
                    }

                } else {
                    emailFocusRequester.requestFocus()
                    isEmailError = true
                    isPasswordError = true
                    createToast(context, message = R.string.check_your_credentials)
                }


            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue
            )

        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center

            )
        }

        CustomHyperLink(
            fullText = stringResource(id = R.string.dont_have_an_account),
            linkText = stringResource(id = R.string.sign_up),
            signupNavigation = signupNavigation
        )


    }

}

fun createToast(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}