package com.example.dailygreen_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    var firestore : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    lateinit var edt_email : EditText
    lateinit var edt_password : EditText
    lateinit var btn_signUp : Button
    lateinit var btn_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        edt_email = findViewById(R.id.edt_email_login)
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

        btn_login = findViewById(R.id.btn_backlogin)
        btn_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // 회원가입
    fun signUp(){
        auth?.createUserWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())
            ?.addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    // users에 추가
                    var user = auth!!.currentUser
                    if (user != null) {
                        firestore?.collection("users")?.document(user.uid)
                            ?.set(hashMapOf("email" to edt_email.text.toString(), "password" to edt_password.text.toString()))
                    }
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