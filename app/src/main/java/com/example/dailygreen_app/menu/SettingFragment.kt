package com.example.dailygreen_app.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dailygreen_app.BeforLoginActivity
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SettingFragment : Fragment(){
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null

    lateinit var btn_logout : ImageButton
    lateinit var btn_terms : ImageButton
    lateinit var btn_producer : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_setting, container, false)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()

        btn_logout = view.findViewById(R.id.btn_logout)
        btn_terms = view.findViewById(R.id.btn_terms)
        btn_producer = view.findViewById(R.id.btn_producer)

        // 로그아웃
        btn_logout.setOnClickListener {
            logout()

        }

        // 이용 약관
        btn_terms.setOnClickListener {
            showTerms()
        }

        // 제작사
        btn_producer.setOnClickListener {
            showProducer()
        }

        return view
    }

    fun logout(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_mylist, null)
        var textView = dialogView?.findViewById<TextView>(R.id.text_check_delete)
        if (textView != null) {
            textView.text = "로그아웃하시겠습니까?"
        }

        builder.setView(dialogView)
            .setPositiveButton("확인"){ dialogInterFace, i ->
                auth?.signOut()
                val intent = Intent(activity, BeforLoginActivity::class.java)
                activity?.let { ContextCompat.startActivity(it, intent, null) }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    @SuppressLint("SetTextI18n")
    fun showTerms(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_mylist, null)
        var textView = dialogView?.findViewById<TextView>(R.id.text_check_delete)
        if (textView != null) {
            textView.setPadding(42,0,42,0)
            textView.text = "데일리그린에 오신 것을 환영합니다!" + "\n\n" +
                            "당사가 명시적으로 별도의 약관이 적용된다고 밝히지 않는 한 본 이용 약관이 귀하의 데일리그린 사용에 적용되며 서비스에 대한 정보를 제공합니다. \n\n" +
                            "귀하가 데일리그린 계정을 만들거나 데일리그린을 이용하면 귀하는 본 약관에 동의하는 것입니다."
        }

        builder.setView(dialogView)
            .setTitle("이용 약관")
            .setPositiveButton("닫기"){ dialogInterface: DialogInterface, i: Int -> }
            .show()
    }

    @SuppressLint("SetTextI18n")
    fun showProducer(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_mylist, null)
        var textView = dialogView?.findViewById<TextView>(R.id.text_check_delete)
        if (textView != null) {
            textView.setPadding(42,0,42,0)
            textView.text = "안녕하세요! 서울여대 플렌테라피하시조 입니다." + "\n\n" +
                    "집에 있는 시간이 늘어나고 있는 요즘, 나만의 식물로 힐링을 해보는건 어떨까요? 실제 반려식물을 가꾸고 돌보면서 느낄 수 있는 " +
                    "소소한 행복과 기쁨이 정서적 힐링감을 안겨준다고 합니다. \n\n" +
                    "모두 힘든 시기, 우리 같이 힘내서 이겨내봅시다. \n화이팅!"
        }

        builder.setView(dialogView)
            .setTitle("제작자 소개")
            .setPositiveButton("닫기"){ dialogInterface: DialogInterface, i: Int -> }
            .show()
    }
}