package com.example.myplayer.UpperNavigationView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myplayer.Model.UpperNavigation
import com.example.myplayer.SampleLayout.PreviewAndNext
import com.example.myplayer.SampleLayout.TeamNext
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel


@Composable
fun  UpperNavigationView(
    matchID: String,
    trackViewmodel: TrackViewModel,
    t1shortName: String,
    t2shortName: String,
    navController: NavController,
    t1Name: String,
    t2Name: String,
    time: Long,
    t1Pic: String,
    t2Pic: String,
)
{


    val context = LocalContext.current

    val innerNavController = rememberNavController()

    val wicketCount by trackViewmodel.wickketKeeperCount
    val batCount by trackViewmodel.batsmanCount
    val allRounderCount by trackViewmodel.allRounderCount
    val    bowlCount by  trackViewmodel.bowlerCount

    Column(modifier = Modifier.fillMaxWidth()) {




        val item = listOf(
            UpperNavigation(route = "WK", label = "WK($wicketCount)"),
            UpperNavigation(route = "BAT", label = "BAT($batCount)"),
            UpperNavigation(route = "AR", label = "AR($allRounderCount)"),
            UpperNavigation(route = "BOWL", label = "BOWL($bowlCount)")
        )



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        ) {

            Scaffold(bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp).padding(start = 5.dp)
                        .background(brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.White))),
                    contentAlignment = Alignment.CenterStart
                )
                {
                    Row {
                        if (trackViewmodel.isTeamValid())
                        {

                            PreviewAndNext(navController,trackViewmodel,t1Name,t2Name)
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        if (trackViewmodel.isTeamValid())
                        {

                            TeamNext(navController,
                                trackViewmodel,t1Name,
                                t2Name,time,matchID,
                                t1shortName,t2shortName,
                                t1Pic,t2Pic)
                        }

                    }
                }

            }
                ,
                topBar = {
                    NavigationBar(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .height(60.dp)
                            .padding(top = 1.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Blue, Color(0xFF5A9CFF))
                                )
                            ), containerColor = Color.Transparent
                    )
                    {
                        val currentstate =
                            innerNavController.currentBackStackEntryAsState().value?.destination?.route
                        item.forEach { item ->
                             NavigationBarItem(

                                selected = currentstate == item.route,
                                label = {
                                    Text(
                                        text = item.label,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.ExtraBold, color = Color.White
                                        )
                                    )
                                },
                                icon = { Box(modifier = Modifier.size(height = 0.dp, width = 0.dp))
                                },

                                onClick = {
                                    innerNavController.navigate(item.route)
                                    {
                                        launchSingleTop = true
                                        popUpTo(innerNavController.graph.startDestinationId)
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
                    navController = innerNavController ,
                    startDestination = "WK",
                    modifier = Modifier.padding(innerpadding)
                )
                {

                    composable("WK") {

                        Wicket(matchID,trackViewmodel)

                    }
                    composable("AR") {
                        Allrounder(matchID,trackViewmodel)
                    }

                    composable("BOWL")
                    {
                       Bowling(matchID,trackViewmodel)
                    }

                    composable("BAT")
                    {
                        Batting(matchID,trackViewmodel)
                    }
                }

            }
        }

    }
}