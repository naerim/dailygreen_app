package com.example.dailygreen_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
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
            moveCategory("공기정화")
        }

        btn_pet.setOnClickListener {
            moveCategory("반려동물")
        }

        btn_dry.setOnClickListener {
            moveCategory("물조금만")
        }

        btn_dark.setOnClickListener {
            moveCategory("햇빛조금")
        }

        btn_interior.setOnClickListener {
            moveCategory("인테리어")
        }

        btn_killbut.setOnClickListener {
            moveCategory("해충박멸")
        }

        return view
    }

    // 카테고리값을 listRecommendFragment로 넘겨주는 함수
    fun moveCategory(category: String?){
        val listRecommendFragment = ListRecommendFragment()
        val bundle = Bundle()
        bundle.putString("category", "$category")
        listRecommendFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.main_content, listRecommendFragment)?.commit()
    }
}