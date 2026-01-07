package com.example.myplayer.UpperNavigationView.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.Model.TeamViewCategorized
import kotlinx.coroutines.flow.StateFlow

class TrackViewModel:ViewModel()
{
    private  val _selectedPlayerID = mutableStateOf<Set<String>>(emptySet())
    val selectedPlayerID: State<Set<String>> get() = _selectedPlayerID

    val selectedRoles = mutableStateOf<Map<String, String>>(emptyMap())


    private val _selectedCountPerTeam = mutableStateOf<Map<String, Int>>(emptyMap())

    val selectedCountPerTeam: State<Map<String, Int>> get() = _selectedCountPerTeam

    var squadList by mutableStateOf<List<MatchSquadModel>>(emptyList())

    // wicket ka count karega
    val wickketKeeperCount:State<Int> = derivedStateOf {
        squadList.count{it.role.equals("WK-Batsman", true)&& _selectedPlayerID.value.contains(it.id) }
    }

    // batting  ka count karega
    val batsmanCount:State<Int> = derivedStateOf {
        squadList.count{it.role.equals("Batsman",true) && selectedPlayerID.value.contains(it.id)}
    }

    // all rounder count karega
    val allRounderCount: State<Int> = derivedStateOf {
        squadList.count { it.role.equals("Bowling Allrounder", true) && selectedPlayerID.value.contains(it.id) }
    }

    // bowl ka count karega
    val bowlerCount: State<Int> = derivedStateOf {
        squadList.count { it.role.equals("Bowler", true) && selectedPlayerID.value.contains(it.id) }
    }



    fun UpdateSquadList(currentSquad: List<MatchSquadModel>) {
        squadList = currentSquad
    }

    fun togglePlayerSelection(
        PlayerId: String,
        context: Context,
    ) {


        val player = squadList.find { it.id == PlayerId }


        if (player == null) return

        val role = player.role ?: "" // Role ko player se nikaalein
        val teamName: String = if (!player.shortname.isNullOrEmpty()) {

            player.shortname ?: ""
        } else {
            if ((player.teamName?.length ?: 0) >= 4) {
                player.teamName?.substring(0, 4) ?: ""
            } else {
                player.teamName ?: ""
            }
        }




        val count = when(role){

            "WK-Batsman"-> wickketKeeperCount.value
            "Batsman" ->  batsmanCount.value
            "Bowling Allrounder" -> allRounderCount.value
            "Bowler" -> bowlerCount.value
            else->0
        }

        val currenTeamCount = _selectedCountPerTeam.value[teamName]?:0

        val updated = _selectedPlayerID.value.toMutableSet()

        if (updated.contains(PlayerId)) {
            updated.remove(PlayerId)

            _selectedCountPerTeam.value = _selectedCountPerTeam.value.toMutableMap().apply {

                if (currenTeamCount > 1) {
                    put(teamName, currenTeamCount - 1)
                } else {
                    remove(teamName)
                }
            }

        } else {
            if (updated.size >= 11) {
                Toast.makeText(context, "Max 11 players allowed", Toast.LENGTH_SHORT).show()
                return
            }
            if (count >= 5)
            {
                Toast.makeText(context, "$role , should be less than 5" , Toast.LENGTH_SHORT).show()
                return
            }


            if (currenTeamCount >= 7) {
                Toast.makeText(context, "Only 7 players allowed from $teamName", Toast.LENGTH_SHORT).show()
                return
            }
            updated.add(PlayerId)

            _selectedCountPerTeam.value = _selectedCountPerTeam.value.toMutableMap().apply {
                put(teamName, currenTeamCount + 1)
            }
        }

        _selectedPlayerID.value = updated
        Log.d("SELECTED", "After: ${selectedPlayerID.value}")
        Log.d("MatchCountTeam", "togglePlayerSelection: ${selectedCountPerTeam.value}")

    }



    fun isTeamValid(): Boolean {
        val wk = wickketKeeperCount.value
        val bat = batsmanCount.value
        val ar = allRounderCount.value
        val bowl = bowlerCount.value

        val total = wk + bat + ar + bowl

        return total == 11 && wk >= 1 && bat >= 1 && ar >= 1 && bowl >= 1 // checking minimum 1 player must be selected
    }

    // for my priviewTeam where user can see his team
    fun getPlayersByIdForTeamPreview(): TeamViewCategorized
    {

        val selectedId = selectedPlayerID.value
        val allPlayers = squadList

        val selectedPlayers = allPlayers.filter { it.id in selectedId }

        val wk = selectedPlayers.filter {  it.role.equals("WK-Batsman" , ignoreCase = true) }
        val bat = selectedPlayers.filter { it.role.equals("Batsman", ignoreCase = true) }
        val Ar = selectedPlayers.filter { it.role.equals("Bowling Allrounder", ignoreCase = true)}
        val bow = selectedPlayers.filter { it.role.equals("Bowler", ignoreCase = true) }

        return TeamViewCategorized(
            wk = wk,
            bat = bat,
            bowl = bow,
            all =  Ar
        )

    }


    fun onSelectRole(role: String, playerId: String) {
        val currentMap = selectedRoles.value.toMutableMap()
        val otherRole = if (role == "C") "VC" else "C"

        when {
            // If player is already selected for the role, remove (toggle off)
            currentMap[role] == playerId -> {
                currentMap.remove(role)
            }

            // If player is selected in the other role, remove from other & assign to current
            currentMap[otherRole] == playerId -> {
                currentMap.remove(otherRole)
                currentMap[role] = playerId
            }

            // Otherwise, assign normally
            else -> {
                currentMap[role] = playerId
            }
        }

        selectedRoles.value = currentMap
    }
}