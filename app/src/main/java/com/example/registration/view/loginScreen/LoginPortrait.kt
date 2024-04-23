package com.example.registration.view.loginScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registration.R
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.view.utils.CustomEmail
import com.example.registration.view.utils.CustomHyperLink
import com.example.registration.view.utils.CustomPassword

@Composable
fun LoginPortrait(
    email: String,
    password: String,
    passwordStringCallback: (password: String) -> Unit,
    emailStringCallBack: (email: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.shopping_bag_logo),
            contentDescription = "Logo",
            modifier = modifier
                .fillMaxWidth()
        )

        Text(
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            color = DarkGreen

        )

        Text(
            text = "Login to continue using the app",
            color = Color.Black.copy(alpha = 0.5f),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),

            )
        CustomEmail(
            email = email,
            emailStringCallback = emailStringCallBack
        )
        CustomPassword(
            password = password,
            passwordStringCallback = passwordStringCallback
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            onClick = {
                if (checkEmailAndPassword(email = email, password = password)) {
                    Toast.makeText(context, "Log in success", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Log in failed", Toast.LENGTH_SHORT).show()
                }
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

        CustomHyperLink(
            fullText = "Don't have an account ",
            linkText = "Sign up"
        )


    }

}
