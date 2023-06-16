package com.example.kotlin_subproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_subproject.databinding.ActivityLoginBinding
import com.example.kotlin_subproject.databinding.ActivityMainBinding
import com.example.kotlin_subproject.singleton.mGoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.root)

        mainBinding!!.logoutBtn.setOnClickListener {
            signOut()
        }

        mainBinding!!.revokeBtn.setOnClickListener {
            revokeAccess()
        }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener{
                val gotologin = Intent(this@MainActivity, Login::class.java)
                startActivity(gotologin)
                finish()
            }
    }
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener{
                val gotologin = Intent(this@MainActivity, Login::class.java)
                startActivity(gotologin)
                finish()
            }
    }
}