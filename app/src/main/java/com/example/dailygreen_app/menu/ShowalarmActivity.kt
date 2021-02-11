package com.example.dailygreen_app.menu

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ShowalarmActivity : AppCompatActivity() {
    var firestore : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var user : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showalarm)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        getWindow().addFlags((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED))

        var btn_stop: Button
        btn_stop = findViewById(R.id.btn_stopalarm)

        val player = MediaPlayer.create(this, R.raw.loveagain)
        player.start()

        // intent와 연결
        var id = intent.getStringExtra("id")

        btn_stop.setOnClickListener {
            player.stop()
            player.release()
            //  울린 알람 삭제
            if (id != null) {
                deleteAlarm(id)
            }
            finish()
        }
    }

    fun deleteAlarm(id: String){
        firestore?.collection("users")?.document(user!!.uid)?.collection("alarm")
            ?.document(id)
            ?.delete()
            ?.addOnSuccessListener {}
            ?.addOnFailureListener {}
    }
}