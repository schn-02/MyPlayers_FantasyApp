package com.example.myplayer.Authentication


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myplayer.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun signin(navController: NavController)
{
    val context = LocalContext.current



    var mobileNumber by remember { mutableStateOf("") }

    Card() {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Red)
                    )
                )
                .padding(12.dp)
        )
        {


            Text(
                text = "Sign In ",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp, color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            Text(
                text = "Enter your Signin Credentials ",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp, color = Color.White,
                    fontWeight = FontWeight.Thin
                ),
                modifier = Modifier.padding(bottom = 13.dp)
            )

            TextFields(mobileNumber,

                onMobileChange ={
                    mobileNumber=it
                }
            )

            Spacer(
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp, top = 10.dp)
            )




            loginGoogle()
            {
                if (mobileNumber.isEmpty())
                {
                    Toast.makeText(context, "Please Provide Your Mobile number", Toast.LENGTH_SHORT).show()

                }
                else{

                    navController.navigate("MobileOtp/$mobileNumber")



                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(mobileNumber: String,
               onMobileChange: (String) -> Unit,

               )
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp)),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(4.dp ,
            brush = Brush.linearGradient(colors = listOf(Color(0xffFF3636), Color.Blue)))
    )
    {


        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Red)
                    )
                )
                .padding(16.dp)
        )

        {



            Column {


                // Enter Mobile number

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange =  {
                        if (it.length <=10)
                        {
                            onMobileChange(it)
                        }}
                    ,
                    label = {
                        Text(
                            text = "Enter your Mobile Number ",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,

                                ),
                            modifier = Modifier.background(color = Color.White)
                        )
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    ,

                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black, fontWeight = FontWeight.ExtraBold,

                        ),


                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color(0xFFCCCCCC),
                        unfocusedBorderColor = Color(0xFFEEEEEE)
                    )


                )


            }

        }
    }

}

@Composable
fun loginGoogle(onLoginClick: () -> Unit)
{
    Button(onClick = onLoginClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier.fillMaxWidth()

    ) {

        Text(text = "Sign In" ,
            style = TextStyle(color = Color.White),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,

            )
    }



    Text(text = "OR"  , style = TextStyle(color = Color.White ,
        fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(12.dp)
    )

    Button(onClick = {} ,
        colors = ButtonDefaults.buttonColors(
            containerColor =Color.White,

            ),
        modifier = Modifier.fillMaxWidth()

    ) {

        Row() {

            Image(painter = painterResource(R.drawable.google ) ,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "Sign In with Google",
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp)


            )

        }
    }


}
