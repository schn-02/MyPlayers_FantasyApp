package com.example.myplayer.BottomNavigationView

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myplayer.BottomNavigationView.viewmodel.HomeViewModel
import com.example.myplayer.Model.UserDetialsModel
import com.example.myplayer.R
import com.example.myplayer.SampleLayout.HomeLayout
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val viewmodel: HomeViewModel = viewModel()
    val matches by viewmodel.match.collectAsState()
    val isRefreshing by viewmodel.isRefreshing.collectAsState()

    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            // ViewModel ko batayein ki refresh karna hai
            viewmodel.refreshMatches()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            pullToRefreshState.endRefresh()
        }
    }

    Column{


        TopBar(viewmodel)

        Box(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Blue
                    )
                )
            ).fillMaxSize().nestedScroll(pullToRefreshState.nestedScrollConnection)
        )
        {

            if (matches.isEmpty())
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                {
                    CircularProgressIndicator()

                }
            }
            else {


                LazyColumn {
                    items(matches) { item ->



                        // Har value ko encode karne se pehle check karein
                        val t1Pic = if (item.team1Pic.isNullOrBlank()) "null" else item.team1Pic

                        val t2Pic = if (item.team2Pic.isNullOrBlank()) "null" else item.team2Pic

                            val timeStamp =if(item.timeMilliSeconds==0L) 0L else item.timeMilliSeconds

                        val t1Short = if (item.team1ShortName.isNullOrBlank()) "null" else item.team1ShortName

                        val t2Short = if (item.team2ShortName.isNullOrBlank()) "null" else item.team2ShortName

                        val team1Name = if (item.team1.isNullOrBlank()) "null" else item.team1

                        val team2Name = if (item.team2.isNullOrBlank()) "null" else item.team2

                        // Ab in sahi values ko encode karein
                        val encodedT1Pic = URLEncoder.encode(t1Pic, "UTF-8")
                        val encodedT2Pic = URLEncoder.encode(t2Pic, "UTF-8")
                        val encodedT1Short = URLEncoder.encode(t1Short, "UTF-8")
                        val encodedT2Short = URLEncoder.encode(t2Short, "UTF-8")
                        val encodedteam1name = URLEncoder.encode(team1Name, "UTF-8")
                        val encodedteam2name = URLEncoder.encode(team2Name, "UTF-8")

//                  route hai :-   composable("selectPlayers/{matchId}/{t1Pic}/{t2Pic}/{time}/{t1shortName}/{t2shortName}/{t1name}/{t2name}") {

                        Box(
                            modifier = Modifier.clickable {
                                Log.d("TIMENOTEUU", "TeamNext: ${item.timeMilliSeconds}")

                                navController.navigate(

                                    "selectPlayers/${item.matchID}/$encodedT1Pic/$encodedT2Pic/$timeStamp/$encodedT1Short/$encodedT2Short/$encodedteam1name/$encodedteam2name"
                                )
                            }
                        ) {
                            HomeLayout(item)
                        }
                    }
                }
            }

            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        // Jab refresh nahi ho raha hai (isRefreshing false hai),
                        // toh uski size zero kar do, taaki woh dikhe hi na.
                        val scale = if (pullToRefreshState.isRefreshing) 1f else 0f
                        scaleX = scale
                        scaleY = scale
                    },
                state = pullToRefreshState,
            )

        }



    }
}


@Composable
fun TopBar(viewmodel: HomeViewModel) {


    var context = LocalContext.current




LaunchedEffect(true) {
     viewmodel.getUserDetails()
}

    val userDetails :UserDetialsModel = viewmodel.details.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf( Color.Blue,Color(0xFF5A9CFF)))
            )
            .padding(horizontal = 16.dp,
                vertical = 10.dp)
            .clip(RoundedCornerShape(topStart =  10.dp)),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.user), // user profile image
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text("Hello,", color = Color.White, fontSize = 14.sp)
                Text(userDetails.name, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = "Wallet",
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )
            Text("₹${if (userDetails.walletBalance.isEmpty()){
                   "0"
                }else{
                    userDetails.walletBalance
            }
            }", color = Color.White, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "Notifications",
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

