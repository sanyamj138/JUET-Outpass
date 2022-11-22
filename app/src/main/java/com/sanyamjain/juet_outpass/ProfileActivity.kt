package com.sanyamjain.juet_outpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener{
            val intent = Intent(this, ApplicationActivity::class.java)
            startActivity(intent)
        }

    }
}