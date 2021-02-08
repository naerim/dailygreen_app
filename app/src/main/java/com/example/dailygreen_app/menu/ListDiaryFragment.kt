package com.example.dailygreen_app.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.MyListDetailActivity
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.ArrayList

class ListDiaryFragment : Fragment(){
    lateinit var btn_back : Button
    var name : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_diary_list, container, false)

        btn_back = view.findViewById(R.id.btn_back)

        val extra = arguments
        if (extra != null){
            name = extra.getString("name")
        }

        Toast.makeText(activity, "$name", Toast.LENGTH_SHORT).show()


        btn_back.setOnClickListener {
            val diaryFragment = DiaryFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.main_content, diaryFragment)
                ?.commit()
        }

        return view
    }
}