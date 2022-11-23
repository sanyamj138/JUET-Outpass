package com.sanyamjain.juet_outpass

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import androidx.core.view.isVisible
import java.util.*
import java.util.Calendar.YEAR

class ApplicationActivity : AppCompatActivity() {
    private lateinit var calendarFrom: EditText
    private lateinit var calendarTo: EditText
    private lateinit var calendarViewFrom: CalendarView
    private lateinit var calendarViewTo: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)

        calendarFrom = findViewById(R.id.calendarFrom)
        calendarTo = findViewById(R.id.calendarTo)
        calendarViewFrom = findViewById(R.id.calendarViewFrom)
        calendarViewTo = findViewById(R.id.calendarViewTo)

         calendarFrom.setOnClickListener(){
             View.VISIBLE
        }

        calendarTo.setOnClickListener(){

        }

    }
}