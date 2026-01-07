package com.example.myplayer.UpperNavigationView.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.Retrofit.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayersViewmodel :ViewModel(){

    private  val  _Squad  = MutableStateFlow<List<MatchSquadModel>>(emptyList())
    val squad : StateFlow<List<MatchSquadModel>> =_Squad

        suspend fun loadAllCategories(matchID: String) {
            Log.d("DEBUG_VM", "1. loadAllCategories function SHURU HUA for matchID: $matchID")
            try {
                coroutineScope {
                    val batsDeferred = async { RetrofitClient.AuthData.getBatMatchSquad(matchID) }
                    val allRoundersDeferred = async { RetrofitClient.AuthData.getAllRounderMatchSquad(matchID) }
                    val bowlersDeferred = async { RetrofitClient.AuthData.getBowlMatchSquad(matchID) }
                    val wicketsDeferred = async { RetrofitClient.AuthData.getWicketMatchSquad(matchID) }

                    Log.d("DEBUG_VM", "2. Saari API calls ek saath shuru ho gayi hain...")

                    val batList = batsDeferred.await()
                    val arList = allRoundersDeferred.await()
                    val bowlList = bowlersDeferred.await()
                    val wkList = wicketsDeferred.await()

                    Log.d("DEBUG_VM", "3. Saari API calls se response mil gaya hai.")
                    Log.d("DEBUG_VM", "   - Batsmen mile: ${batList.size}")
                    Log.d("DEBUG_VM", "   - AllRounders mile: ${arList.size}")
                    Log.d("DEBUG_VM", "   - Bowlers mile: ${bowlList.size}")
                    Log.d("DEBUG_VM", "   - WicketKeepers mile: ${wkList.size}")

                    val fullSquadList = mutableListOf<MatchSquadModel>()
                    fullSquadList.addAll(batList)
                    fullSquadList.addAll(arList)
                    fullSquadList.addAll(bowlList)
                    fullSquadList.addAll(wkList)

                    Log.d("DEBUG_VM", "4. Sabko jodkar total players hue: ${fullSquadList.size}")

                    _Squad.value = fullSquadList
                    Log.d("DEBUG_VM", "5. StateFlow ko nayi list ke saath update kar diya gaya hai.")
                }
            } catch (e: Exception) {
                // YEH SABSE ZAROORI LOG HAI
                Log.e("DEBUG_VM", "ERROR! API call fail ho gayi. Wajah: ${e.message}", e)
            }
        }











}