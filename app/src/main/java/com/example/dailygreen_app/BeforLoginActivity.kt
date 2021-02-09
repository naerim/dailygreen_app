package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BeforLoginActivity : AppCompatActivity() {

    lateinit var btn_login : Button
    lateinit var btn_signin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_befor_login)

        btn_login = findViewById(R.id.btn_mainlogin)
        btn_signin = findViewById(R.id.btn_mainsignin)

        btn_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btn_signin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}