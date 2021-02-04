package com.example.dailygreen_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MyListDetailActivity : AppCompatActivity() {
    lateinit var text_plantname : TextView
    lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_detail)

        text_plantname = findViewById(R.id.text_plantname)

        val name = intent.getStringExtra("name")
        text_plantname.text = name

    }
}
