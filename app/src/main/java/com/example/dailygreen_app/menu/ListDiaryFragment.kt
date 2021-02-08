package com.example.dailygreen_app.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.R
import java.text.SimpleDateFormat
import java.util.*

class ListDiaryFragment : Fragment(){
    lateinit var btn_back : Button
    lateinit var text_name_diary_detail : TextView
    var name : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_diary_list, container, false)

        btn_back = view.findViewById(R.id.btn_back)
        text_name_diary_detail = view.findViewById(R.id.text_name_diary_detail)

        // DiaryFragment에서 데이터 전달 받기
        val extra = arguments
        if (extra != null){
            name = extra.getString("name")
        }
        text_name_diary_detail.text = name

        // 현재 날짜, 시간 불러오기
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val getTime = sdf.format(date)
        Toast.makeText(activity, "$getTime", Toast.LENGTH_SHORT).show()

        btn_back.setOnClickListener {
            val diaryFragment = DiaryFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.main_content, diaryFragment)
                ?.commit()
        }

        return view
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary_detail, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
        }

    }
}