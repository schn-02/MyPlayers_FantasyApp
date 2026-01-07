package com.example.myplayer.Retrofit

import com.example.myplayer.Model.HomeMatch
import com.example.myplayer.Model.MatchSquadModel
import com.example.myplayer.Model.SigninModel
import com.example.myplayer.Model.UserDetialsModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface
{
    @GET("/api/AuthCheck")
    suspend fun getAuthData():Response<SigninModel>

    @POST("/api/SaveAuthData")
    suspend fun sendAuthData(@Body result: SigninModel): Response<SigninModel>

    @GET("/api/homeMatches")
    suspend fun getHomeMatches(@Query("refresh")refresh:Boolean):List<HomeMatch>

    @GET("/api/upcomingMatches")
    suspend fun getUpcomingMatches(@Query("refresh")refresh:Boolean):List<HomeMatch>

    @GET("/api/UserDetails")
    suspend fun getDetails():Response<UserDetialsModel>


    @GET("/api/FetchWicketSquad")
    suspend fun getWicketMatchSquad(@Query("matchID") matchID: String): List<MatchSquadModel>

    @GET("/api/FetchBatSquad")
    suspend fun getBatMatchSquad(@Query("matchID") matchID: String): List<MatchSquadModel>

    @GET("/api/FetchBowlSquad")
    suspend fun getBowlMatchSquad(@Query("matchID") matchID: String): List<MatchSquadModel>


    @GET("/api/FetchAllRounderSquad")
    suspend fun getAllRounderMatchSquad(@Query("matchID") matchID: String): List<MatchSquadModel>




}