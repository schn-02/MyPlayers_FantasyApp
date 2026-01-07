package com.example.myplayer.Authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplayer.Model.SigninModel
import com.example.myplayer.Retrofit.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// API call aur UI ke states ko handle karne ke liye
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class OtpVerified(val message: String) : AuthUiState()
    object UserExists : AuthUiState()
    object NewUser : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    class ProfileCreated(val message: String):AuthUiState()
}


class AuthViewmodel:ViewModel() {

    private val _uiState= MutableStateFlow<AuthUiState>(AuthUiState.Idle)
      val uiState : StateFlow<AuthUiState> = _uiState

    // check ho rha hai ki credentiall(otp , number) shi hai ya nhi simple word me otp verify ho rha hai
    fun signinCredentials(credential: PhoneAuthCredential)
    {
        _uiState.value = AuthUiState.Loading
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener {task->
                if (task.isSuccessful) {
                    _uiState.value = AuthUiState.OtpVerified("Welcome")
                    checkUserDetails()
                }

                else{
                    _uiState.value = AuthUiState.Error("Invalid OTP")
                }
            }




    }



    // check karta hai ki user exit hai ya nhi spring boot ko call laga kar

    private  fun checkUserDetails()
    {
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            if (FirebaseAuth.getInstance().currentUser==null)
            {
                _uiState.value = AuthUiState.Error("User not logged in")
                return@launch
            }
            else{

                try {


                    val response = RetrofitClient.AuthData.getAuthData()
                    if (response.isSuccessful) {
                        _uiState.value = AuthUiState.UserExists
                    } else if (response.code() == 404) {
                        _uiState.value = AuthUiState.NewUser

                    } else {
                        _uiState.value = AuthUiState.Error("Invalid Try Again")
                    }
                }
                catch (e:Exception)
                {
                    _uiState.value = AuthUiState.Error("Invalid Try Again!!")
                }
            }
        }
    }


    // api call karke signin data save kar rhe hai
    fun SaveEnterDetails(name:String, email:String, mobileNumber:String)
    {

        _uiState.value = AuthUiState.Loading

        viewModelScope.launch {

            try {


                 val data = SigninModel(name,mobileNumber, email)
                val response = RetrofitClient.AuthData.sendAuthData(data)

                if (response.isSuccessful)
                {
                    _uiState.value = AuthUiState.ProfileCreated(response.body().toString())
                }


            }
            catch (e:Exception)
            {
                Log.d("TAG", "ERROR : $e")
            }
        }
    }


}

