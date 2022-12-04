package com.sanyamjain.juet_outpass

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.daos.UserDao
import com.sanyamjain.juet_outpass.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Login_Activity : AppCompatActivity(){
    private val RC_SIGN_IN: Int = 123
    private val TAG = "SignInActivity Tag"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        signInButton.setOnClickListener {
            val a=spinner.text.toString()
            if(a=="Student")
            signIn()
            if(a=="Warden") {
                // TODO:Implement warden login
            }
            if(a=="Supre"){
                // TODO: Implement supre login
            }
            if(a=="Parent"){
                //TODO: Implement parent login
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {
                updateUI(firebaseUser)
            }
        }

    }
    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {
            if(firebaseUser.email.toString().endsWith("juetguna.in")){
                signInButton.visibility=View.GONE
                auth = Firebase.auth
                val currentUser=auth.currentUser!!
                val doc=usersCollection.document(currentUser.uid)
                doc.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                           val i = Intent(this, HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            val user = User(
                                firebaseUser.uid,
                                firebaseUser.displayName,
                                firebaseUser.email.toString(),
                                "",
                                0,
                                0,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                0
                            )
                            val usersDao = UserDao()
                            usersDao.addUser(user)

                            val i = Intent(this,HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.exception)
                    }
                }

//                val profileActivityIntent = Intent(this, ProfileActivity::class.java)
//                startActivity(profileActivityIntent)
//                finish()

                }else{
                signInButton.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                    Firebase.auth.signOut()
                googleSignInClient.revokeAccess()
                Toast.makeText(this,"Sign In with JUET account",Toast.LENGTH_LONG).show()
            }

        } else {
            signInButton.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}