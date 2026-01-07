package com.example.myplayer.Authentication


import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myplayer.Animation.SiginAnimation
import com.google.firebase.auth.PhoneAuthProvider
@Composable
fun MobileOtp(mobilenumber: String, navController: NavController) {



    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("") }
    var verificationID by rememberSaveable { mutableStateOf("") }

    val authviewmodel:AuthViewmodel = viewModel()
    val uiState by authviewmodel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush =
                Brush.linearGradient(colors = listOf(Color.Blue, Color.Red))

            )
            .padding(top = 100.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hi Champion",
                style = TextStyle(
                    color = Color.Green,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )



        }

        Spacer(modifier = Modifier.height(12.dp)) // 👈 Correct Spacer


        SiginAnimation()

        Spacer(modifier = Modifier.padding(top = 12.dp))


        Text(
            text = "Verify your Phone Number",
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold

            )
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))

        Text(
            text = "Enter Your OTP Here",
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold

            )
        )

        Spacer(modifier = Modifier.padding(12.dp))

        OtpInputBox(6)
        {
            otpValue = it

        }

        val otpContext = LocalActivity.current as Activity


        GenrateOTP(phoneNumber = mobilenumber, activity = otpContext, onCodeSent = {id->
            verificationID =id},
            onVerificationCompleted = { credential ->
                authviewmodel.signinCredentials(credential)
            },
            onVerificationFailed = {
                Toast.makeText(context, "Verification Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        )



        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            onClick = {
//                       verifyCode(otpValue , verificationID , context)

                if (otpValue.length == 6 && verificationID.isNotEmpty()) {
                   val credential = PhoneAuthProvider.getCredential(verificationID, otpValue)
                    authviewmodel.signinCredentials(credential)

                } else {
                    Toast.makeText(context, "Please enter 6 digit OTP", Toast.LENGTH_SHORT).show()
                }

            }) {

            Text(
                text = "Verify", style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                ), color = Color.White
            )
        }


    }


    LaunchedEffect(uiState) {
         when(val state = uiState)
         {
             is AuthUiState.UserExists->{
                 navController.navigate("main")
                 {
                     popUpTo("MobileOtp"){
                         inclusive=true
                     }
                 }

             }
             is AuthUiState.NewUser->{
                 navController.navigate("notUser/$mobilenumber"){
                     popUpTo("MobileOtp"){inclusive =true}
                 }
             }
             is AuthUiState.Error -> {
                 Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
             }
             is AuthUiState.OtpVerified -> {
                 Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
             }
             else -> { /* Idle ya Loading state me kuch nahi karna */ }
         }
    }
}




@Composable
fun OtpInputBox(
    otpLength: Int = 6,
    onOtpComplete: (String) -> Unit
) {
    var otpValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    // To auto-focus text field when the Composable is shown
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { focusRequester.requestFocus() }, // Click anywhere to focus
        contentAlignment = Alignment.Center
    ) {
        // OTP Circles
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(otpLength) { index ->
                val char = otpValue.getOrNull(index)?.toString() ?: ""
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            if (char.isNotEmpty()) {

                                Color(0xff2A3232)
                            } else {
                                Color(0xFFE0E0E0)
                            }
                        )
                )
                {
                    Text(
                        text = char,
                        style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
                        color = if (char.isNotEmpty()) {
                            Color.Green
                        } else {
                            Color.White
                        }
                    )
                }
            }
        }

        // Transparent TextField overlapping the Row to capture input

        BasicTextField(
            value = otpValue,
            onValueChange = { it ->
                if (it.length <= otpLength && it.all { ch -> ch.isDigit() }) {
                    otpValue = it

                    if (it.length == otpLength) {
                        onOtpComplete(it)
                    }
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { }
                .width((60.dp + 12.dp) * otpLength) // match row width
                .height(60.dp)
                .alpha(0f), // invisible
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
