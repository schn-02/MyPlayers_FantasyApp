package com.example.myplayer.Authentication

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun EnterDetailsLogin( number :String ,navController: NavHostController)
{
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

     val authViewmodel:AuthViewmodel = viewModel()

    Card() {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Red)
                    )
                )
                .padding(12.dp)
                .padding(top = 140.dp)
        )
        {


            Text(
                text = "Hi Champion",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp, color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            Text(
                text = "Fill Your Details ",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 30.sp, color = Color.White,
                    fontWeight = FontWeight.Thin
                ),
                modifier = Modifier.padding(bottom = 13.dp)
            )

            TextFieldssss(name , email,
                onNameChange ={ name=it},
                onEmailChange = {email=it}
            )

            Spacer(
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp, top = 10.dp)
            )




            enter()
            {

                if (name.isEmpty())
                {
                    Toast.makeText(context, "Please enter your name  ", Toast.LENGTH_SHORT).show()

                }
                else{

                    authViewmodel.SaveEnterDetails(name, email,number)



                }
            }



        }
    }

    val uiState by authViewmodel.uiState.collectAsState()
    LaunchedEffect(uiState) {

        if (uiState is AuthUiState.ProfileCreated)
        {
            navController.navigate("main"){
                popUpTo("MobileOtp"){inclusive=true}
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldssss(  name :String, email:String ,
                    onNameChange:(String)->Unit,
                    onEmailChange:(String)->Unit,
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
                    value = name,
                    onValueChange =  {
                        if (it.length <=17)
                        {
                            onNameChange(it)
                        }}
                    ,
                    label = {
                        Text(
                            text = "Enter your Name",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,

                                ),
                            modifier = Modifier.background(color = Color.White)
                        )
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    ,

                    textStyle = TextStyle(
                        fontSize = 12.sp,
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

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange
                    ,
                    label = {
                        Text(
                            text = "Enter your Email ID (Optional)",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,

                                ),
                            modifier = Modifier.background(color = Color.White)
                        )
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    ,

                    textStyle = TextStyle(
                        fontSize = 12.sp,
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
fun enter(onLoginClick: () -> Unit)
{
    Button(onClick = onLoginClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier.fillMaxWidth()

    ) {

        Text(text = "Save" ,
            style = TextStyle(color = Color.White),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,

            )
    }



}

