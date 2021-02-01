package com.example.dailygreen_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailygreen_app.menu.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottom_navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.action_home -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, homeFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_diary -> {
                    val diaryFragment = DiaryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, diaryFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_alarm -> {
                    val alarmFragment = AlarmFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_calender -> {
                    val calendarFragment = CalenderFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, calendarFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_recommend -> {
                    val recommendFragment = RecommendFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, recommendFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

}