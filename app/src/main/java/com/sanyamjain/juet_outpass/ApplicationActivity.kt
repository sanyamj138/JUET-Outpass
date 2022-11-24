package com.sanyamjain.juet_outpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.daos.PostDao
import com.sanyamjain.juet_outpass.daos.UserDao
import com.sanyamjain.juet_outpass.models.Post
import com.sanyamjain.juet_outpass.models.User
import kotlinx.android.synthetic.main.activity_application.*

class ApplicationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        val currentUser=auth.currentUser!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)
        submitButton.setOnClickListener {
            val post = Post(
                currentUser.uid,
                purpose.text.toString(),
                placeOnLeave.text.toString(),
                "Applied")
            val postDao = PostDao()
            postDao.addPost(post)
        }
    }
}