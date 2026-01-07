package com.example.myplayer.Animation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myplayer.R
import kotlinx.coroutines.delay

@Composable
fun SiginAnimation()
{

    val animationList = listOf(
        R.raw.otpverification

    )

    val currerntIndex = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3600)
            currerntIndex.value = (currerntIndex.value + 1) % animationList.size
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationList[currerntIndex.value])
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp).padding(top = 10.dp)
    )
}

@Composable
fun OtpAnimation()
{

    val animationList = listOf(
        R.raw.smileyemoji

    )

    val currerntIndex = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3600)
            currerntIndex.value = (currerntIndex.value + 1) % animationList.size
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationList[currerntIndex.value])
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .height(80.dp).padding(top = 10.dp)
    )
}
