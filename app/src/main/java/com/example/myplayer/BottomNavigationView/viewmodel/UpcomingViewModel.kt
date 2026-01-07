package com.example.myplayer.BottomNavigationView.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplayer.Model.HomeMatch
import com.example.myplayer.Model.UserDetialsModel
import com.example.myplayer.Retrofit.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpcomingViewModel:ViewModel()
{
    private  val  _matches = MutableStateFlow<List<HomeMatch>>(emptyList())
    val matches:StateFlow<List<HomeMatch>> = _matches

    private val _details = mutableStateOf(UserDetialsModel())
    val details : State<UserDetialsModel> = _details

    private   val _isRefreshing = MutableStateFlow(false)
    val isRefreshing:StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {

        fetchIntialMatches()
    }

    // check ho rha hai ki credentiall(otp , number) shi hai ya nhi simple word me otp verify ho rha hai
    fun fetchIntialMatches()
    {
        viewModelScope.launch {
            if (_matches.value.isEmpty())
            {
                try {
                    FetchHomeApi(false)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error fetching initial matches: ${e.message}")
                } finally {
                    // Yeh hamesha chalega
                    _isRefreshing.value = false
                    Log.d("HomeViewModel", "Initial fetch finished. isRefreshing set to false.")
                }
            }

        }


    }

    fun  refreshMatches() {
        viewModelScope.launch {
            _isRefreshing.value = true
            Log.d("HomeViewModel", "Refresh started...")
            try {
                // API se data layega
                FetchHomeApi(true)
            } catch (e: Exception) {
                // Agar error aaye toh log karein
                Log.e("HomeViewModel", "Error refreshing matches: ${e.message}")
            } finally {
                // Yeh block hamesha chalega, chahe success ho ya error
                _isRefreshing.value = false
                Log.d("HomeViewModel", "Refresh finished. isRefreshing set to false.")
            }
        }
    }

    private  suspend  fun FetchHomeApi(isRefreshing: Boolean)
    {
        try {

            val response = RetrofitClient.AuthData.getUpcomingMatches(refresh = isRefreshing)
            if (response.isNotEmpty())
            {
                _matches.value = response

            }
            else{
                Log.d("TAG", "Invalid try Again")
            }

        }
        catch (e:Exception)
        {
            Log.e("ViewModel", "Exception: ${e.message}")
        }




    }

    fun getUserDetails()
    {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.AuthData.getDetails()

                if (response.isSuccessful && response.body() != null) {
                    _details.value = response.body()!!
                } else {
                    Log.d("ViewModel", "API Error: ${response.code()} ${response.message()}")
                }

            }
            catch (e:Exception)
            {
                Log.e("ViewModel", "Exception: ${e.message}")

            }
        }
    }

}