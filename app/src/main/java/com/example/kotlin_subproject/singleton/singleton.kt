package com.example.kotlin_subproject.singleton

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignInClient

val TAG = "jsjeon"

lateinit var mGoogleSignInClient: GoogleSignInClient
lateinit var resultLauncher: ActivityResultLauncher<Intent>

fun showLog(message: String){
    Log.d(TAG,message)
}