package com.example.myplayer.SampleLayout

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SampelLayoutForCVC(player: MatchSquadModel ,selectedRoles: Map<String, String>,
                       onSelectRole: (String, String) -> Unit)
{

    Log.d("CVCC", "SampelLayoutForCVC: $selectedRoles")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(brush = Brush.linearGradient(colors = listOf(Color.White, Color(0xffC6C6C6))))
            .border(
                3.dp,
                color = Color.Green,
                shape = RectangleShape
            )
    )
    {

        Column {
            Row {



                GlideImage(imageModel = {player.playerImg}, loading = {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } , failure = {
                    Image(painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp))
                }, modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                )

                Spacer(Modifier.width(20.dp))

                player.name
                    ?.take(14) // safe: null handle karega aur length check karega
                    ?.let { safeName ->
                        Text(
                            text = safeName,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }



                Spacer(modifier = Modifier.weight(1f))


                Image(
                    painter = painterResource(if (selectedRoles["C"] == player.id)
                    {
                        R.drawable.captainactivated
                    }else{
                        R.drawable.captain

                    }),
                    contentDescription = "Captain",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { player.id?.let { onSelectRole("C", it) } }
                        .border(2.dp, color = Color.Blue , RectangleShape)
                        .padding(5.dp).align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(20.dp))



                Image(
                    painter = painterResource( if (selectedRoles["VC"] == player.id) {
                        R.drawable.vicecaptainactivated
                    }
                    else{
                        R.drawable.vicecaptain
                    }
                    )
                    ,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Vice Captain",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { player.id?.let { onSelectRole("VC", it) } }
                        .border(2.dp, color = Color.Blue , RectangleShape)
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)

                )

                Spacer(Modifier.width(10.dp))

            }

            Row {

                player.country?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold, color = Color.Black
                        ), modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(3.dp))

                (if (player.role.equals("Bowling Allrounder")) {
                    "All-Rounder"
                } else{
                    player.role
                })?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold, color = Color.Black
                        ), modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }


    }
}
