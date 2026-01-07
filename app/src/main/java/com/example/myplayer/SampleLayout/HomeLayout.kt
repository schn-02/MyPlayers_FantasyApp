package com.example.myplayer.SampleLayout

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myplayer.Model.HomeMatch
import com.example.myplayer.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeLayout(homeMatch: HomeMatch) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(30.dp)),
        border = BorderStroke(2.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF000428),
                            Color(0xFF004e92)
                        )
                    )
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                // 🔹 SERIES NAME
                Text(
                    text = homeMatch.seriesName.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                )

                Spacer(modifier = Modifier.padding(5.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    GlideImage(imageModel = {homeMatch.team1Pic},
                        loading = { CircularProgressIndicator(modifier = Modifier.size(20.dp)) }
                        , failure = {
                            Image(painter = painterResource(R.drawable.cricket),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp))
                        }, modifier = Modifier.size(60.dp).clip(CircleShape)

                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = homeMatch.time.toString(),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace
                        )
                    )


                    Spacer(modifier = Modifier.weight(1f))



                    GlideImage(
                        imageModel = { homeMatch.team2Pic },
                        loading = {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        },
                        failure = {
                            Image(
                                painter = painterResource(R.drawable.cricket),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(60.dp)
                    )

                }

                Spacer(modifier = Modifier.padding(top = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {

                    homeMatch.team1?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier.width(120.dp)

                        )
                    }

                    homeMatch.date?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Yellow,
                                fontFamily = FontFamily.Monospace

                            ),
                            modifier = Modifier.padding(top = 70.dp)
                        )
                    }


                    homeMatch.team2?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier.fillMaxWidth().padding(start = 40.dp)

                        )
                    }


                }
            }
        }
    }
}

