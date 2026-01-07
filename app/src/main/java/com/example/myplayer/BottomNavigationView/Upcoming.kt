package com.example.myplayer.BottomNavigationView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.myplayer.BottomNavigationView.viewmodel.UpcomingViewModel
import com.example.myplayer.Model.UserDetialsModel
import com.example.myplayer.R
import com.example.myplayer.SampleLayout.HomeLayout
import com.example.myplayer.SampleLayout.UpcomingSampleLayout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Upcoming() {
    val viewmodel: UpcomingViewModel = viewModel()
    val matches by viewmodel.matches.collectAsState()
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


        UpcomingTopBar(viewmodel)

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



            LazyColumn {
                items(matches) { item ->



                    Box{
                        UpcomingSampleLayout(item)
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
fun UpcomingTopBar(viewmodel: UpcomingViewModel) {


    var context = LocalContext.current




    LaunchedEffect(true) {
        viewmodel.getUserDetails()
    }

    val userDetails : UserDetialsModel = viewmodel.details.value

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



