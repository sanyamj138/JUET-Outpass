package com.sanyamjain.juet_outpass

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.models.User
import com.sanyamjain.juet_outpass.models.Warden
import kotlinx.android.synthetic.main.activity_faculty_profile.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.studentSem
import kotlinx.android.synthetic.main.activity_profile.submitButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FacultyProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val wardenCollection = db.collection("wardens")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_profile)


        auth = Firebase.auth
        val currentUser=auth.currentUser!!
        val docRef = db.collection("wardens").document(currentUser.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val u = document.toObject<Warden>()
                    val editableName = SpannableStringBuilder(u!!.superintendentName)
                    superintendentNameValue.text=editableName
                    val editableID = SpannableStringBuilder(u!!.superintendentID)
                    superintendentEmailValue.text=editableID
                    val year = SpannableStringBuilder(u!!.year)
                    studentYr.text=year
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        submitButton.setOnClickListener{
            GlobalScope.launch {
                val doc=wardenCollection.document(currentUser.uid)
                doc.update("year",studentYr!!.text.toString())
                doc.update("superintendentName",superintendentNameValue!!.text.toString())
                doc.update("superintendentID",superintendentEmailValue!!.text.toString())
            }
            val intent = Intent(this, HomeActivityFaculty::class.java)
            startActivity(intent)
            finish()
        }
    }
}