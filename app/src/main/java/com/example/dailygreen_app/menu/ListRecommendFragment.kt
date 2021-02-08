package com.example.dailygreen_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dailygreen_app.MainActivity
import com.example.dailygreen_app.R

class ListRecommendFragment : Fragment(){

    lateinit var btn_back : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_recommend_list, container, false)

        // 데이터 전달받기
        var tag : String? = null
        var extra = arguments
        if(extra != null) {
            tag = extra.getString("tag")
            Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
        }

        btn_back = view.findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            val recommendFragment = RecommendFragment()

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, recommendFragment)?.commit()
        }

        return view
    }
}