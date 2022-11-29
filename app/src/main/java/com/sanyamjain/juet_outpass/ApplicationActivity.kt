package com.sanyamjain.juet_outpass

import android.app.DatePickerDialog
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
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import androidx.core.view.isVisible
import java.util.*
import java.util.Calendar.YEAR

class ApplicationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var calendarFrom: EditText
    private lateinit var calendarTo: EditText
    private lateinit var calendarViewFrom: CalendarView
    private lateinit var calendarViewTo: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {

        calendarFrom = findViewById(R.id.calendarFrom)
        calendarTo = findViewById(R.id.calendarTo)
        calendarViewFrom = findViewById(R.id.calendarViewFrom)
        calendarViewTo = findViewById(R.id.calendarViewTo)

         calendarFrom.setOnClickListener(){
             View.VISIBLE
        }

        calendarTo.setOnClickListener(){

        }
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