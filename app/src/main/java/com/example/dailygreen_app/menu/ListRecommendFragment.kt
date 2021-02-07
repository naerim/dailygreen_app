package com.example.dailygreen_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        btn_back = view.findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            val recommendFragment = RecommendFragment()

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, recommendFragment)?.commit()
        }

        return view
    }
}