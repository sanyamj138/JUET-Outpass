package com.sanyamjain.juet_outpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.models.Post
import com.sanyamjain.juet_outpass.models.User
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        applicationButton.setOnClickListener{
            val intent = Intent(this, PreviousApplicationActivity::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)

        }

        applyApplication.setOnClickListener {
            auth=Firebase.auth
            db.collection("post").whereEqualTo("userID", auth.currentUser!!.uid)
                .whereEqualTo("status","Applied")
                .get()
                .addOnSuccessListener{ documents->
                        if(documents.isEmpty){
                        val intent = Intent(this, ApplicationActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Your Application is Pending",Toast.LENGTH_SHORT).show()
                    }
            }
        }

        logoutButton.setOnClickListener {
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