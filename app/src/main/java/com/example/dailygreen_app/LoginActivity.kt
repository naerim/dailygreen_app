package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    lateinit var btn_login : Button
    lateinit var btn_signUp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login = findViewById(R.id.btn_login)
        btn_signUp = findViewById(R.id.btn_signUp)

        btn_signUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }
}