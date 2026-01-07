package com.example.myplayer.Model

import com.google.gson.annotations.SerializedName


data class HomeMatch(

    @SerializedName("seriesName")
    var seriesName:String?="",

    @SerializedName("time")
    var time:String?="",

    @SerializedName("matchTimestampMillis")
    var timeMilliSeconds:Long?=0L,

    @SerializedName("date")
    var date:String?="",

    @SerializedName("team1")
    var team1:String?="",

    @SerializedName("team2")
    var team2:String?="",

    @SerializedName("team1Pic")
    var team1Pic:String?="",


    @SerializedName("team2Pic")
    var team2Pic:String?="",

    @SerializedName("matchID")
    var matchID:String?="",


    @SerializedName("team1ShortName")
    var team1ShortName:String?="",


    @SerializedName("team2ShortName")
    var team2ShortName:String?="",


    @SerializedName("playerName")
    var playerName:String?="",

    var playerID:String?="",

    var playerRole:String?="",

    var playerBattingStyle:String?="",

    var playerImage:String?="",

    var playerCountry:String?="",

    var playerShortTeamName:String?="",

    var playerTeamPic:String?="",

    var matchType:String?="" ,

    var matchStatus:String?="",

)


