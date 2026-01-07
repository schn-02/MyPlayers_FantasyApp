package com.example.myplayer.Model

data class  TeamViewCategorized(
    val wk: List<MatchSquadModel>,
    val bat: List<MatchSquadModel>,
    val bowl: List<MatchSquadModel>,
    val all: List<MatchSquadModel>
)
