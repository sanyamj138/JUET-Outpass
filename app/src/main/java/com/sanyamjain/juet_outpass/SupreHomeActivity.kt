package com.sanyamjain.juet_outpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_faculty.*

class SupreHomeActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supre_home)
        val s=intent.getStringExtra("Year")

        previousApplications.setOnClickListener {
            val intent = Intent(this, SuprePreviousActivity::class.java)
            intent.putExtra("Year",s)
            startActivity(intent)
        }
        currentApplications.setOnClickListener {
            val intent = Intent(this, SupreCurrentActivity::class.java)
            intent.putExtra("Year",s)
            startActivity(intent)
        }
        lgoutButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            auth = Firebase.auth
            Firebase.auth.signOut()
            googleSignInClient.revokeAccess()
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}