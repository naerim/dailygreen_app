package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    lateinit var edt_email : EditText
    lateinit var edt_password : EditText
    lateinit var btn_signUp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()

        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)
        btn_signUp = findViewById(R.id.btn_signUp)

        btn_signUp.setOnClickListener {
            if(edt_email.text.toString() == ""){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(edt_password.text.toString() == ""){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                signUp()
            }
        }
    }

    // 회원가입
    fun signUp(){
        auth?.createUserWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())
            ?.addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    moveLoginPage(task.result?.user)
                }else{
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun moveLoginPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}