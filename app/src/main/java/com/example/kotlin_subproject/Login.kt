package com.example.kotlin_subproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlin_subproject.databinding.ActivityLoginBinding
import com.example.kotlin_subproject.databinding.ActivityMainBinding
import com.example.kotlin_subproject.singleton.mGoogleSignInClient
import com.example.kotlin_subproject.singleton.resultLauncher
import com.example.kotlin_subproject.singleton.showLog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Login : AppCompatActivity() {

    var loginBinding: ActivityLoginBinding? = null

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Not Yet", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding!!.root)

        setResultSignUp()

        // todo: .requestIdtoken()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        loginBinding!!.googleloginbtn.setOnClickListener {
            signIn()
        }
    }

    private fun setResultSignUp() {
        val gotomain = Intent(this, MainActivity::class.java)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    startActivity(gotomain)
                    finish()
                    handleSignInResult(task)
                }
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()
            val photoUrl = account?.photoUrl.toString()

            showLog("이메일 : $email")
            showLog("이름 : $familyName $givenName $displayName")
            showLog("프로필 사진 주소 : $photoUrl")
        } catch (e: ApiException) {
            Log.w("failed", "signInResultFailed code = " + e.statusCode)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

}