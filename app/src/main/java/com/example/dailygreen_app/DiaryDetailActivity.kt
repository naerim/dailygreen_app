package com.example.dailygreen_app

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dailygreen_app.menu.MyList
import com.example.dailygreen_app.menu.Plants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class DiaryDetailActivity : AppCompatActivity() {
    lateinit var text_date_diary_detail1 : TextView
    lateinit var edt_text_diary_detail1 : EditText
    lateinit var btn_close_diary : Button
    lateinit var btn_delete_diary : Button
    lateinit var btn_edit_diary : Button
    lateinit var btn_back_diary : Button

    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null
    var content : String? = null
    var date : String? = null
    var id : String? = null
    lateinit var diarylist: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)

        text_date_diary_detail1 = findViewById(R.id.text_date_diary_detail1)
        edt_text_diary_detail1 = findViewById(R.id.edt_text_diary_detail1)
        btn_close_diary = findViewById(R.id.btn_close_diary)
        btn_delete_diary = findViewById(R.id.btn_delete_diary)
        btn_edit_diary = findViewById(R.id.btn_edit_diary)
        btn_back_diary = findViewById(R.id.btn_back_diary)

        diarylist = arrayListOf<String>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // intent와 연결
        content = intent.getStringExtra("content")
        date = intent.getStringExtra("date")
        id = intent.getStringExtra("id")
        edt_text_diary_detail1.setText("$content")
        text_date_diary_detail1.text = date

        // 뒤로가기
        btn_back_diary.setOnClickListener {
            finish()
        }

        // 창 닫기
        btn_close_diary.setOnClickListener {
            finish()
        }

        // 다이어리 삭제
        btn_delete_diary.setOnClickListener {
            showDeleteDialog()
        }

        // 다이어리 수정
        btn_edit_diary.setOnClickListener {
            showEditDialog()
        }
    }

    fun showDeleteDialog(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete_mylist, null)

        val alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("확인") {dialog, which ->
                firestore?.collection("users")?.document(user!!.uid)?.collection("diary")
                    ?.document("$id")
                    ?.delete()
                    ?.addOnSuccessListener {
                        finish()
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {}
            }
            .setNegativeButton("취소", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    fun showEditDialog(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete_mylist, null)
        var textView = view.findViewById<TextView>(R.id.text_check_delete)
        textView.text = "수정하시겠습니까?"

        val alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("확인") {dialog, which ->
                firestore?.collection("users")?.document(user!!.uid)?.collection("diary")
                    ?.document("$id")
                    ?.update(hashMapOf("content" to edt_text_diary_detail1.text.toString()) as Map<String, Any>)
                        ?.addOnSuccessListener {
                        Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {}
            }
            .setNegativeButton("취소", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }
}