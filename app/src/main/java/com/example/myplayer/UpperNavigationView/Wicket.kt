package com.example.myplayer.UpperNavigationView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myplayer.SampleLayout.SelectPlayersSampleLayout
import com.example.myplayer.UpperNavigationView.viewModel.PlayersViewmodel
import com.example.myplayer.UpperNavigationView.viewModel.TrackViewModel

@Composable
fun Wicket(
    matchID: String,
    trackViewmodel: TrackViewModel
) {

    val selectedPlayerIds = trackViewmodel.selectedPlayerID.value


    val context = LocalContext.current


     val completeSquadList =trackViewmodel.squadList

    Log.d("completeSquadList", "Wicket: $completeSquadList")

     val wicketSquad = completeSquadList.filter{
         it.role.equals("WK-Batsman",true)

     }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        if (completeSquadList.isEmpty())
        {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()

            }
        }
        else {
            LazyColumn {
                // Filter kī huī list ko yahan display karein
                items(wicketSquad) { item ->
                    val isSelected = selectedPlayerIds.contains(item.id)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            item.id?.let { trackViewmodel.togglePlayerSelection(it, context) }
                        })
                    {
                        SelectPlayersSampleLayout(item, isSelected)
                    }
                }
        }
    }
}
}

