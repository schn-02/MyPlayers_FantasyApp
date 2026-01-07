package com.example.myplayer.SampleLayout

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.example.myplayer.R
import com.example.myplayer.UpperNavigationView.UpperNavigationView
import com.example.myplayer.UpperNavigationView.viewModel.PlayersViewmodel
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLEncoder

@Composable
fun ChoosePlayerSampleLayout(
    matchID: String,
    navController: NavController,
    t1Pic: String,
    t2Pic: String,
    time: Long,
    t1shortName: String,
    t2shortName: String,
    t1Name: String,
    t2name:String,
    trackViewmodel:TrackViewModel= viewModel()

    ) {

     val viewModel: PlayersViewmodel = viewModel()

     val countMap by trackViewmodel.selectedCountPerTeam
    val totalPlayerCount = trackViewmodel.selectedPlayerID.value.size

    Log.d("TIMENOTEUUPPER", "TeamNext: $time")

    LaunchedEffect(Unit) {

          viewModel.loadAllCategories(matchID)
      }

    val fullSquadData by viewModel.squad.collectAsState()



    LaunchedEffect(fullSquadData) {
        trackViewmodel.UpdateSquadList(fullSquadData)
        Log.d("completeSquadList", "Wicket: $fullSquadData")

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Green, Color.White, Color.Green, Color.White, Color.Green)
                )
            )
    ) {
        // Main content in Column
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            TopBar(time)
            TeamView(matchID, navController,
                t1Pic, t2Pic,
                t1shortName, t2shortName,
                t1Name,t2name, time,trackViewmodel,countMap,totalPlayerCount)


        }

    }
}

@Composable
fun TopBar(time: Long) {

    Log.d("TimeChoose", "TopBar: $time")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(topStart = 20.dp))
            .height(56.dp)
    ) {
        // 👈 Left Icon
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )

        // 👇 Centered MatchCountdown using wrapper Box
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 20.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.Black) {
                MatchCountdown(time)
            }
        }

        // 👉 Right Icon
        Image(
            painter = painterResource(R.drawable.eye),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        )
    }
}


@Composable
fun TeamView(
    matchID: String,
    navController: NavController,
    t1Pic: String,
    t2Pic: String,
    t1shortName: String,
    t2shortName: String,
    t1Name: String,
    t2name: String,
    time: Long,
    trackViewmodel: TrackViewModel,
    countMap: Map<String, Int>,
    totalPlayerCount: Int, )
{

    Card(modifier = Modifier.clip(RoundedCornerShape(topStart = 40.dp , topEnd = 40.dp))
    ) {


        Column (modifier = Modifier
            .background(
                brush = Brush
                    .linearGradient(colors = listOf(Color.Blue, Color.Red))
            )
            .fillMaxSize()
            .padding(20.dp)

        ) {

            Row(modifier = Modifier.align(Alignment.Start)){
                Text(
                    text = "Max 7 player from a team",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold
                    ), modifier = Modifier.padding(start = 100.dp)
                )

            }

            Spacer(Modifier.padding(10.dp))
            Row {

                Text(
                    text = "Players", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold

                    )
                )


                Spacer(Modifier.width(60.dp))

                Text(
                    text = t1shortName, style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold,

                        )
                )

                Spacer(Modifier.width(102.dp))

                Text(
                    text = t2shortName, style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold
                    ),
                )
            }


            Spacer(Modifier.padding(top = 5.dp))
            Row {

                Text(
                    text = "$totalPlayerCount/ 11", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center


                    ) ,modifier = Modifier
                        .width(60.dp) ,
                    maxLines = 1
                )


                Spacer(Modifier.width(50.dp))


                GlideImage(imageModel = {if (t1Pic.isNotEmpty()){
                    t1Pic
                }
                else{
                    R.drawable.cricket
                }
                },
                    loading ={ CircularProgressIndicator(modifier = Modifier.size(20.dp))},
                    failure = { Image(painter = painterResource(R.drawable.cricket) , contentDescription = null, modifier = Modifier.size(50.dp))
                    }, modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)

                )

                val selectedCountTeam1 = countMap[t1shortName]?:0
                val selectedCountTeam2 = countMap[t2shortName]?:0

                Spacer(Modifier.width(5.dp))
                Text(
                    text = "$selectedCountTeam1", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold
                    ), modifier = Modifier.padding(top = 14.dp)
                )


                Spacer(Modifier.width(50.dp))

                Text(
                    text = "$selectedCountTeam2", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White, fontWeight = FontWeight.ExtraBold
                    ), modifier = Modifier.padding(top = 14.dp)
                )
                Spacer(Modifier.width(5.dp))


                GlideImage(imageModel =  { if (t2Pic.isNotEmpty()){
                    t2Pic
                }
                else{
                    R.drawable.cricket
                }
                },
                    loading ={ CircularProgressIndicator(modifier = Modifier.size(20.dp))},
                    failure = { Image(painter = painterResource(R.drawable.cricket) , contentDescription = null, modifier = Modifier.size(50.dp))
                    }, modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)

                )



            }

            Spacer(Modifier.padding(top = 12.dp))
            PlayerSelectionBar(totalPlayerCount)

            Spacer(Modifier.padding(top = 12.dp))

            HorizontalDivider(color = Color.White, thickness = 1.dp , modifier = Modifier.fillMaxWidth())

            MatchInfoDetails()

            Spacer(Modifier.padding(top = 12.dp))

            HorizontalDivider(color = Color.White, thickness = 1.dp , modifier = Modifier.fillMaxWidth())

            UpperNavigationView(matchID,trackViewmodel,t1shortName,t2shortName,navController,t1Name, t2name,time,t1Pic, t2Pic)


        }
    }

}


@Composable
fun PlayerSelectionBar(selectedPlayer: Int)
{
    val  totalPlayers = 11


    Row(verticalAlignment = Alignment.CenterVertically) {
        Row {

            for (i in 0 until totalPlayers) {
                Box(

                    modifier = Modifier
                        .size(height = 15.dp, width = 30.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(
                            if (i < selectedPlayer) {
                                Color.Green
                            } else {
                                Color.White
                            }
                        )
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp))

                )
                {

                }
                Spacer(Modifier.padding(1.dp))
            }
        }
    }

}


@Composable
fun MatchInfoDetails()
{

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)) {
        Text(text = "Cricket Venu " , style = TextStyle(color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        ), modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}



@Composable
fun PreviewAndNext(
    navController: NavController,
    viewModel: TrackViewModel,
    t1Name: String,
    t2Name: String
)
{



    Column{
        Card {
            Button(
                onClick = {
                    navController.navigate("previewMyteam/$t1Name/$t2Name")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow  // Background green
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            )
            {

                Text(
                    text = "Preview",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.right), // ← Drawable image
                    contentDescription = "Preview Icon",
                    tint = Color.Black, // Icon color
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}



@Composable
fun TeamNext(
    navController: NavController,
    viewModel: TrackViewModel,
    t1Name: String?,
    t2Name: String?,
    time: Long?,
    matchID: String?,
    t1shortName: String?,
    t2shortName: String?,
    t1Pic: String?,
    t2Pic: String?
) {
    val isNavigationSafe = !matchID.isNullOrBlank()

    val route = remember(
        matchID, t1Name, t2Name, t1shortName, t2shortName, t1Pic, t2Pic
    ) {
        if (isNavigationSafe) {
            val encodedMatchID = URLEncoder.encode(matchID!!, "UTF-8")
            val encodedT1Name = URLEncoder.encode(t1Name.orEmpty(), "UTF-8")
            val encodedT2Name = URLEncoder.encode(t2Name.orEmpty(), "UTF-8")
            val encodedT1Short = URLEncoder.encode(t1shortName.orEmpty(), "UTF-8")
            val encodedT2Short = URLEncoder.encode(t2shortName.orEmpty(), "UTF-8")
            val encodedT1Pic = URLEncoder.encode(t1Pic.orEmpty(), "UTF-8")
            val encodedT2Pic = URLEncoder.encode(t2Pic.orEmpty(), "UTF-8")

            // Match EXACT NavGraph order
            "MyteamCVC/$encodedMatchID/$encodedT1Name/$encodedT2Name/$encodedT1Short/$encodedT2Short/$encodedT1Pic/$encodedT2Pic"
        } else null
    }

    Column {
        Card {
            Button(
                onClick = { route?.let { navController.navigate(it) } },
                enabled = isNavigationSafe,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.right),
                    contentDescription = "Preview Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
