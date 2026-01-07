package com.example.myplayer.PreviewTeam

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.R
import com.example.myplayer.SampleLayout.MatchCountdown
import com.example.myplayer.SampleLayout.SampelLayoutForCVC
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel

@Composable
fun SelectCVC(
    navController: NavHostController,
    viewmodel: TrackViewModel,
    matchID: String,
    t1Name: String,
    t2Name: String,
    t1shortName: String,
    t2shortName: String,
    t1Pic: String,
    t2Pic: String
) {

    val players = viewmodel.getPlayersByIdForTeamPreview()
    Log.d("CHECKING", "SelectCVC: $players")



    val wk = players.wk
    val bat = players.bat
    val all = players.all
    val bowler = players.bowl

    val playerSections = listOf(
        "Wicket Keepers" to wk,
        "Batsmen" to bat,
        "All Rounders" to all,
        "Bowlers" to bowler
    )

    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Blue))
            )
            .fillMaxSize()
            .statusBarsPadding()
    ) {
//        if (time != null) {
//            TopBarCVC(time)
//        }
        TeamViewCVC()

        LazyColumn(modifier = Modifier.weight(1f)){
            playerSections.forEach { (title, playersList) ->
                if (playersList.isNotEmpty()) {
                    item {
                        HorizontalDivider(
                            thickness = 3.dp,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }


                    items(playersList) { player ->


                        SampelLayoutForCVC(  player = player,
                            selectedRoles = viewmodel.selectedRoles.value,
                            onSelectRole = viewmodel::onSelectRole
                        )


                    }
                }
            }
        }

        Box(modifier = Modifier.padding(bottom = 16.dp)) {
            if (viewmodel.selectedRoles.value.size==2) {
                BottomBarCVCSave(matchID,
                    viewmodel,playerSections,
                    t1Name,t2Name,t1shortName,t2shortName,t1Pic,t2Pic
                )
            }

        }



    }

}





@Composable
fun TopBarCVC(time: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(56.dp)
            .background(brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.White)))
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
                .padding(top = 10.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.Green) {
                MatchCountdown(time)
            }
        }

        // 👉 Right Icon
        Image(
            painter = painterResource(R.drawable.questionmark),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        )
    }
}


@Composable
fun TeamViewCVC ()
{
    Column {

        Text(text = "Choose  your Captain and Vice Captain" ,
            style = TextStyle(Color.White),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        Text(text = "C gets 2x points , VC gets 1.5x points" ,
            style = TextStyle(Color.White),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )




    }


}



@Composable
fun BottomBarCVCSave(
    matchID: String,
    viewmodel: TrackViewModel,
    playerSections: List<Pair<String, List<MatchSquadModel>>>,
    t1Name: String,
    t2Name: String,
    t1shortName: String,
    t2shortName: String,
    t1Pic: String,
    t2Pic: String,

    ) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(56.dp)
            .background(brush = Brush.linearGradient(colors = listOf(Color.Yellow, Color.Green)))
            .clickable {
                Log.d(
                    "CLICK",
                    "BottomBarCVCSave: HELLO SACHIN"
                )
            }
    ) {


        Text(
            text = "Save", style = TextStyle(fontSize = 20.sp,
                fontWeight = FontWeight.Bold , color = Color.Black

            ), modifier = Modifier
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(R.drawable.right),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        )
    }
}
