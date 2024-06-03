package com.example.registration.view.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.R
import com.example.registration.ui.theme.White


@Composable
fun CustomEmail(
    modifier: Modifier = Modifier,
    email: String,
    emailStringCallback: (email: String) -> Unit,
    isEmailError:Boolean,
    focusRequester: FocusRequester
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Email",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .fillMaxWidth(),

            )

        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30),
            value = email,
            onValueChange = {
                emailStringCallback(it)

            },

            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = Color.Black
            ),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter your email..",
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            isError = isEmailError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)




        )


    }
}

@Composable
fun CustomPassword(
    modifier: Modifier = Modifier,
    password: String,
    passwordStringCallback: (password: String) -> Unit,
    isPasswordError:Boolean,
    focusRequester: FocusRequester
) {

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Password",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .fillMaxWidth(),

            )

        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester =focusRequester)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30),
            value = password,
            onValueChange = {
                passwordStringCallback(it)
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = Color.Black
            ),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter your password..",
                    color = Color.Gray
                )
            },

            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    R.drawable.eye_close_ic
                else R.drawable.eye_open_ic

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = image), contentDescription = "password")
                }


            },
            isError = isPasswordError,



        )


    }

}


@Composable
fun CustomHyperLink(
    fullText: String,
    linkText: String,
    signupNavigation:()->Unit,
) {
    val annotatedString = buildAnnotatedString {
        append(fullText )
        pushStringAnnotation(linkText , "")
        withStyle(
            style = SpanStyle(
                Color.Blue,
                textDecoration = TextDecoration.Underline
            ),

            ) {
            append(linkText)
        }

        pop()


    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            signupNavigation()
            annotatedString.getStringAnnotations("tag", offset, offset)
        }
    )


}

