package com.example.myplayer.Interceptor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Interceptor
import okhttp3.Response

class FirebaseInterceptor :Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
         val originalRequest = chain.request()

           val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser==null)
        {
            return  chain.proceed(originalRequest)
        }

        try {
            val idTokenresult = com.google.android.gms.tasks.Tasks.await(currentUser.getIdToken(false))
            val idToken = idTokenresult.token

            if (idToken!=null)
            {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization" , "Bearer $idToken")
                    .build()

                return chain.proceed(newRequest)
            }

        }
        catch (e:Exception)
        {
            Log.d("TAG", "Error $e")
        }

        return  chain.proceed(originalRequest)
    }
}