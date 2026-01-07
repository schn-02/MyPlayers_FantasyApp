package com.example.myplayer.PreviewTeam

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.R
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PreviewTeam(
    navController: NavController,
    viewModel: TrackViewModel,
    t1name: String,
    t2name: String
) {


    Log.d("LODD", "PreviewTeam: ${viewModel.getPlayersByIdForTeamPreview()}")

    val wk = viewModel.getPlayersByIdForTeamPreview().wk
    val batting = viewModel.getPlayersByIdForTeamPreview().bat
    val bowler = viewModel.getPlayersByIdForTeamPreview().bowl
    val allrounder = viewModel.getPlayersByIdForTeamPreview().all




    Box {
        Image(
            painter = painterResource(R.drawable.teamprivew3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp))
        {

            PlayerSection("Wicket Keepers", wk, t1name,t2name)
            PlayerSection("Batters", batting, t1name, t2name)
            PlayerSection("All Rounders", allrounder, t1name, t2name)
            PlayerSection("Bowlers", bowler, t1name, t2name)
        }
    }
}



@Composable
fun PlayerSection(title: String, players: List<MatchSquadModel>, t1shortname: String, t2shortname: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 15.sp),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        // Chunk the players into rows of 3
        val chunked = players.chunked(3)

        chunked.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                horizontalArrangement = Arrangement.Center // 🔥 Center all items
            ) {
                rowItems.forEach { player ->
                    SampleLayoutPlayerCard(player = player,t1shortname,t2shortname)
                }
            }
        }
    }
}



@Composable
fun SampleLayoutPlayerCard(player: MatchSquadModel, team1: String, team2: String) {

    Log.d("KON", "PreviewTeam: ${player.country}")

    val isFromTeam1 = team1?.contains(player.country ?: "", ignoreCase = true) == true


    val textColor = if (isFromTeam1) Color.White else Color.Black
    val bgColor = if (isFromTeam1) Color.Black else Color.White

    Column(
        modifier = Modifier
            .padding(start = 19.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(imageModel = {if(player.playerImg.equals("https://h.cricapi.com/img/icon512.png"))
        {
            R.drawable.user

        }else{
            player.playerImg
        }
        }
            ,
            loading = { CircularProgressIndicator(modifier =
            Modifier.size(20.dp)) },
            failure = {
                Image(painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp))
            }, modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(bottom = 4.dp)
        )


        player.name
            ?.take(14) // safe: null handle karega aur length check karega
            ?.let { safeName ->
                Text(
                    text = safeName,
                    color = textColor,
                    fontSize = 9.sp,
                    modifier = Modifier.background(bgColor)
                )
            }

    }
}
