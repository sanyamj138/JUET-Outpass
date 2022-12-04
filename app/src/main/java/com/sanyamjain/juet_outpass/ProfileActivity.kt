package com.sanyamjain.juet_outpass

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.daos.UserDao
import com.sanyamjain.juet_outpass.models.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        val currentUserName = auth.currentUser!!.displayName.toString()
        val currentUser=auth.currentUser!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val docRef = db.collection("users").document(currentUser.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val u = document.toObject<User>()
                    val editableBranch = SpannableStringBuilder(u!!.branch)
                    studentBranch.text=editableBranch
                    val editableHomeAddress = SpannableStringBuilder(u!!.homeAddress)
                    homeAddress.text=editableHomeAddress
                    val editableHostel = SpannableStringBuilder(u!!.hostel)
                    hostelNumber.text=editableHostel
                    val editableRoom = SpannableStringBuilder(u!!.roomNo)
                    roomNumber.text=editableRoom
                    val editableYear = SpannableStringBuilder(u!!.year.toString())
                    studentYear.text=editableYear
                    val editableSem = SpannableStringBuilder(u!!.sem.toString())
                    studentSem.text=editableSem
                    val editableStudentMobile = SpannableStringBuilder(u!!.studentNumber.toString())
                    studentMobile.text=editableStudentMobile
                    val editableParentMobile = SpannableStringBuilder(u!!.parentNumber.toString())
                    parentMobile.text=editableParentMobile
                    val editableParentEmail=SpannableStringBuilder(u!!.parentEmail)
                    parentEmail.text=editableParentEmail
                    val editableBed = SpannableStringBuilder(u!!.bedNo.toString())
                    bedNumber.text=editableBed
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        submitButton.setOnClickListener{
                GlobalScope.launch {
//                    val userDao=UserDao()
//                    val u=userDao.getUserById(currentUser.uid).await().toObject(User::class.java)!!
                    val doc=usersCollection.document(currentUser.uid)
                    doc.update("branch",studentBranch!!.text.toString())
                    doc.update("homeAddress",homeAddress!!.text.toString())
                    doc.update("hostel",hostelNumber!!.text.toString())
                    doc.update("roomNo",roomNumber!!.text.toString())
                    doc.update("year",studentYear!!.text.toString().toInt())
                    doc.update("sem",studentSem!!.text.toString().toInt())
                    doc.update("studentNumber",studentMobile!!.text.toString())
                    doc.update("parentNumber",parentMobile!!.text.toString())
                    doc.update("parentEmail",parentEmail!!.text.toString())
                    doc.update("bedNo",bedNumber!!.text.toString().toInt())
                }
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
