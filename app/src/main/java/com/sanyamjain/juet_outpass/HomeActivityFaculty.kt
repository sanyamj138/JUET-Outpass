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
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.profileButton
import kotlinx.android.synthetic.main.activity_home_faculty.*

class HomeActivityFaculty : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_faculty)

        profileButton.setOnClickListener {
            val intent = Intent(this, FacultyProfileActivity::class.java)
            startActivity(intent)
        }

        previousApplications.setOnClickListener {
            val intent = Intent(this, WardenPreviousActivity::class.java)
            startActivity(intent)
        }
        currentApplications.setOnClickListener {
            val intent = Intent(this, WardenCurrentActivity::class.java)
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