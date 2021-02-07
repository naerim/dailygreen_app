package com.example.dailygreen_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.dailygreen_app.MainActivity
import com.example.dailygreen_app.R

class RecommendFragment : Fragment(){


    lateinit var btn_freshair : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_recommend, container, false)

        // 여기서 시작!
        btn_freshair = view!!.findViewById(R.id.btn_air)

        btn_freshair.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }


        return view
    }
}