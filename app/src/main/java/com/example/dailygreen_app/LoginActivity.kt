package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    lateinit var edt_email_login : EditText
    lateinit var edt_password_login : EditText
    lateinit var btn_login : Button
    lateinit var btn_signUp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()

        edt_email_login = findViewById(R.id.edt_email_login)
        edt_password_login = findViewById(R.id.edt_password_login)
        btn_login = findViewById(R.id.btn_login)
        btn_signUp = findViewById(R.id.btn_signUp)

        // 이메일 로그인 버튼
        btn_login.setOnClickListener {
            if(edt_email_login.text.toString() == ""){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(edt_password_login.text.toString() == ""){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                emailLogin()
            }
        }

        // 회원가입 버튼
        btn_signUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    fun emailLogin(){
        auth?.signInWithEmailAndPassword(edt_email_login.text.toString(), edt_password_login.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    moveMainPage(task.result?.user)
                }else{
                    resetEditText()
                    Toast.makeText(this, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun resetEditText(){
        edt_email_login.setText("")
        edt_password_login.setText("")
    }
}