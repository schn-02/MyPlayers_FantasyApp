package com.example.myplayer.SampleLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun  SelectPlayersSampleLayout(player: MatchSquadModel, isSelected: Boolean)
{


    Column() {
        PlayerCard(player = player, isSelected)

        Spacer(Modifier.padding(top = 14.dp))


    }
}





@Composable
fun PlayerCard(player: MatchSquadModel, isSelected: Boolean)
{
    Card(modifier = Modifier
        .fillMaxWidth(), shape = RoundedCornerShape(10.dp),

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) {
                        Color.Green
                    } else {
                        Color.Gray
                    }
                ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (player.playerImg!="https://h.cricapi.com/img/icon512.png") {

            }
            GlideImage(imageModel = {if(player.playerImg=="https://h.cricapi.com/img/icon512.png"){
                R.drawable.user
            }
            else{
                player.playerImg
            }
            },
                failure = {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = null, modifier = Modifier.size(60.dp)
                    )
                }, modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
            )



            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                player.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }

                player.teamName?.let {
                    Text(
                        text = it,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally), style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            GlideImage(imageModel = {player.img},
                loading = { CircularProgressIndicator(modifier = Modifier.size(20.dp)) },
                failure = { Image(painter = painterResource(R.drawable.user),
                    contentDescription = null, modifier = Modifier.size(60.dp)) }
                , modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)

            )

        }
    }
}
