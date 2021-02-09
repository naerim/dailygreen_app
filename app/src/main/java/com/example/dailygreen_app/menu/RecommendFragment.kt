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
    lateinit var btn_pet : Button
    lateinit var btn_dry : Button
    lateinit var btn_dark : Button
    lateinit var btn_interior : Button
    lateinit var btn_killbut : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_recommend, container, false)

        // 여기서 시작!
        btn_freshair = view!!.findViewById(R.id.btn_air)
        btn_pet = view!!.findViewById(R.id.btn_pet)
        btn_dry = view!!.findViewById(R.id.btn_dry)
        btn_dark = view!!.findViewById(R.id.btn_dark)
        btn_interior = view!!.findViewById(R.id.btn_interior)
        btn_killbut = view!!.findViewById(R.id.btn_killbug)

        btn_freshair.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "공기정화")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        btn_pet.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "반려동물")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        btn_dry.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "물조금만")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        btn_dark.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "햇빛조금")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        btn_interior.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "인테리어")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        btn_killbut.setOnClickListener {
            val listRecommendFragment = ListRecommendFragment()
            val bundle = Bundle()
            bundle.putString("tag", "해충박멸")
            listRecommendFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
        }

        return view
    }
}