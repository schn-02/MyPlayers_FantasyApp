package com.example.myplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myplayer.Authentication.EnterDetailsLogin
import com.example.myplayer.Authentication.MobileOtp
import com.example.myplayer.Authentication.signin
import com.example.myplayer.BottomNavigationView.Home
import com.example.myplayer.BottomNavigationView.Live
import com.example.myplayer.BottomNavigationView.Profile
import com.example.myplayer.BottomNavigationView.Upcoming
import com.example.myplayer.Model.BottomNavigationView
import com.example.myplayer.PreviewTeam.PreviewTeam
import com.example.myplayer.PreviewTeam.SelectCVC
import com.example.myplayer.SampleLayout.ChoosePlayerSampleLayout
import com.example.myplayer.UpperNavigationView.UpperNavigationView
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel
import com.example.myplayer.ui.theme.MyPlayerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
//        FirebaseAuth.getInstance().signOut()
        enableEdgeToEdge()
        setContent {


            MyPlayerTheme {
                val navController = rememberNavController()


                val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null


                val startdesignation = if (isUserLoggedIn) "main" else "signin"

                NavHost(navController = navController, startDestination = startdesignation) {

                    composable("signin") {
                        signin(navController)
                    }

                    composable("MobileOtp/{mobile}")
                    { backStackEntry ->

                        val mobilenumber = backStackEntry.arguments?.getString("mobile") ?: ""

                        MobileOtp(mobilenumber, navController)
                    }

                    composable("main")
                    {
                        MainScreen(navController)
                    }

                    composable("notUser/{mobile}")
                    {it->
                        val number = it.arguments?.getString("mobile")?:""
                        EnterDetailsLogin( number ,navController)
                    }

                    navigation(
                        route = "gameFlow",
                        startDestination = "selectPlayers/{matchId}/{t1Pic}/{t2Pic}/{timeStamp}/{t1shortName}/{t2shortName}/{t1name}/{t2name}"
                    )
                    {


                        composable("selectPlayers/{matchId}/{t1Pic}/{t2Pic}/{timeStamp}/{t1shortName}/{t2shortName}/{t1name}/{t2name}") {

                            val matchID = it.arguments?.getString("matchId") ?: ""
                            val t1Pic =
                                URLDecoder.decode(it.arguments?.getString("t1Pic") ?: "", "UTF-8")
                            val t2Pic =
                                URLDecoder.decode(it.arguments?.getString("t2Pic") ?: "", "UTF-8")

                            val timeStamp = it.arguments?.getLong("timeStamp")


                            val t1shortName = URLDecoder.decode(
                                it.arguments?.getString("t1shortName") ?: "",
                                "UTF-8"
                            )
                            val t2shortName = URLDecoder.decode(
                                it.arguments?.getString("t2shortName") ?: "",
                                "UTF-8"
                            )
                            val t1Name = URLDecoder.decode(
                                it.arguments?.getString("t1name") ?: "",
                                "UTF-8"
                            )
                            val t2Name = URLDecoder.decode(
                                it.arguments?.getString("t2name") ?: "",
                                "UTF-8"
                            )

                            if (timeStamp != null) {
                                ChoosePlayerSampleLayout(
                                    matchID,
                                    navController,
                                    t1Pic,
                                    t2Pic,
                                    timeStamp,
                                    t1shortName,
                                    t2shortName,
                                    t1Name,
                                    t2Name
                                )
                            }
                        }

                        composable("previewMyteam/{t1Name}/{t2Name}")
                        {
                            val t1Name = it.arguments?.getString("t1Name") ?: ""
                            val t2Name = it.arguments?.getString("t2Name") ?: ""

                            val parentEntry = remember {
                                navController.getBackStackEntry(
                                    "selectPlayers/{matchId}/{t1Pic}/{t2Pic}/{timeStamp}/{t1shortName}/{t2shortName}/{t1name}/{t2name}"
                                )
                            }
                       val viewModel: TrackViewModel = viewModel(parentEntry)

                            PreviewTeam(navController,viewModel,t1Name,t2Name)

                        }

                        composable("MyteamCVC/{matchID}/{t1name}/{t2name}/{t1shortName}/{t2shortName}/{t1Pic}/{t2Pic}")
                        {

                            val matchid = it.arguments?.getString("matchID") ?: ""

                            val t1Pic =
                                URLDecoder.decode(it.arguments?.getString("t1Pic") ?: "", "UTF-8")
                            val t2Pic =
                                URLDecoder.decode(it.arguments?.getString("t2Pic") ?: "", "UTF-8")


                            val t1shortName = URLDecoder.decode(
                                it.arguments?.getString("t1shortName") ?: "",
                                "UTF-8"
                            )
                            val t2shortName = URLDecoder.decode(
                                it.arguments?.getString("t2shortName") ?: "",
                                "UTF-8"
                            )
                            val t1Name = URLDecoder.decode(
                                it.arguments?.getString("t1name") ?: "",
                                "UTF-8"
                            )
                            val t2Name = URLDecoder.decode(
                                it.arguments?.getString("t2name") ?: "",
                                "UTF-8"
                            )

                            val parentEntry = remember {
                                navController.getBackStackEntry(
                                    "selectPlayers/{matchId}/{t1Pic}/{t2Pic}/{timeStamp}/{t1shortName}/{t2shortName}/{t1name}/{t2name}"
                                )
                            }




                            val viewmodel:TrackViewModel= viewModel(parentEntry)

                            SelectCVC(
                                navController,
                                viewmodel,
                                matchid,t1Name,t2Name,t1shortName,t2shortName,t1Pic,t2Pic)

                        }
                    }




                }
}



                }
            }

    }




@Composable
fun MainScreen(navController2: NavController) {


    val systemUiController = rememberSystemUiController()

    // Set status bar color same as your gradient start color
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFF5A9CFF),
            darkIcons = false
        )
    }

    Column(modifier = Modifier.background(color = Color.Blue))
    {


        val navController = rememberNavController()

        val item = listOf(
            BottomNavigationView(route = "Home", icon = R.drawable.cricket, label = "Home"),
            BottomNavigationView(route = "Upcoming", icon = R.drawable.upcoming, label = "Upcoming"),
            BottomNavigationView(route = "Live", icon = R.drawable.live, label = "Live"),
            BottomNavigationView(route = "Profile", icon = R.drawable.profile, label = "Profile")
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(30.dp))
        ) {

            Scaffold(
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier.clip(RoundedCornerShape(bottomStart =30.dp,
                            bottomEnd = 30.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Blue, Color(0xFF5A9CFF))
                                )
                            ), containerColor = Color.Transparent
                    )
                    {
                        val currentstate =
                            navController.currentBackStackEntryAsState().value?.destination?.route
                        item.forEach { item ->

                            NavigationBarItem(
                                icon = {
                                    Image(
                                        painter = painterResource(item.icon),
                                        contentDescription = item.label,
                                        modifier = Modifier.size(30.dp)
                                    )
                                },
                                selected = currentstate == item.route,
                                label = {
                                    Text(
                                        text = item.label,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold, color = Color.White
                                        )
                                    )
                                },

                                onClick = {
                                    navController.navigate(item.route)
                                    {
                                        launchSingleTop = true
                                        popUpTo(navController.graph.startDestinationId)
                                        {
                                            saveState = true
                                        }
                                        restoreState = true
                                    }


                                }

                            )


                        }

                    }
                })
            { innerpadding ->

                NavHost(
                    navController,
                    startDestination = "Home",
                    modifier = Modifier.padding(innerpadding)
                )
                {
                    composable("Home") {
                        Home(navController2)
                    }
                    composable("Live") {
                        Live()
                    }

                    composable("Upcoming")
                    {
                        Upcoming()
                    }

                    composable("Profile")
                    {
                        Profile()
                    }
                }

            }
        }
    }
}


