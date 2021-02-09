package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        // 하단바 제거
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, BeforLoginActivity::class.java))
            finish()
        },SPLASH_TIME)
    }
}