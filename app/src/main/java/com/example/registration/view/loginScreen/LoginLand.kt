package com.example.registration.view.loginScreen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.R
import com.example.registration.constants.InputsRegex
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.view.utils.CustomEmail
import com.example.registration.view.utils.CustomHyperLink
import com.example.registration.view.utils.CustomPassword
import com.example.registration.viewModels.LoginInputFields
import com.example.registration.viewModels.LoginViewModel

@Composable
fun LoginLandScape(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    signupNavigation: () -> Unit,
    successNavigation: () -> Unit,
    loginViewModel: LoginViewModel

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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize(),
                painter = painterResource(id = R.drawable.shopping_bag_logo),
                contentDescription = "Logo",


                )
            CustomHyperLink(
                fullText = "Don't have an account ",
                linkText = "Sign up",
                signupNavigation = signupNavigation
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(0.5f)
        ) {

            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                color = DarkGreen

            )

            Text(
                text = "Login to continue using the app",
                color = Color.Black.copy(alpha = 0.5f),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),

                )
            CustomEmail(
                email = email,
                emailStringCallback = {
                    if (it.matches(regex = Regex(InputsRegex.EMAIL_ALLOWED_REGEX))) {

                        loginViewModel.updateLoginData(text = it, type = LoginInputFields.Email)
                    }
                },
                isEmailError = isEmailError,
                focusRequester = emailFocusRequester,
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
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                onClick = {
                    var toastText = "Invalid credentials"
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        if (loginViewModel.authenticateEmail(email = email)) {

                            isEmailError = false
                            if (loginViewModel.authenticatePassword(password = password)) {
                                isPasswordError = false
                                toastText = "Login success"
                                successNavigation()
                            } else {
                                isPasswordError = true
                                toastText = "Wrong password"
                                passwordFocusRequester.requestFocus()
                            }

                        } else {
                            emailFocusRequester.requestFocus()
                            isEmailError = true
                            toastText = "Email or password wrong"

                        }
                    } else if (email.isEmpty()) {
                        emailFocusRequester.requestFocus()
                        isEmailError = true
                        if (password.isEmpty()) {
                            isPasswordError = true
                        }
                    } else {
                        passwordFocusRequester.requestFocus()
                        isEmailError = true
                        isPasswordError = true
                    }

                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen
                )

            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }


        }


    }


}

