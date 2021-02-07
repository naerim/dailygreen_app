package com.example.dailygreen_app.menu

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.dailygreen_app.R

class ShowalarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showalarm)

        getWindow().addFlags((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED))

        var btn_stop: Button
        btn_stop = findViewById(R.id.btn_stopalarm)

        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//        val ring = RingtoneManager.getRingtone(applicationContext, notification)
//        ring.play()

        val player = MediaPlayer.create(this, R.raw.loveagain)
        player.start()
//
        btn_stop.setOnClickListener {
            player.stop()
            player.release()
            finish()
        }
    }
}