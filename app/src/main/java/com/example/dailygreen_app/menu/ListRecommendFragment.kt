package com.example.dailygreen_app.menu

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.DiaryDetailActivity
import com.example.dailygreen_app.MainActivity
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import java.util.ArrayList

class ListRecommendFragment : Fragment(){
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null
    lateinit var recommend_detail : RecyclerView
    lateinit var text_category : TextView
    lateinit var text_desc : TextView
    lateinit var img_icon : ImageView
    lateinit var btn_back : Button
    var category : String? = null
    lateinit var plantlist: ArrayList<Plants>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_recommend_list, container, false)

        text_category = view.findViewById(R.id.text_category)
        text_desc = view.findViewById(R.id.text_desc)
        img_icon = view.findViewById(R.id.img_icon)
        btn_back = view.findViewById(R.id.btn_back)
        plantlist = arrayListOf<Plants>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 데이터 전달받기
        var extra = arguments
        if(extra != null) {
            category = extra.getString("category")
        }
        // 이미지 설정
        var imgId = setIcon(category)
        if (imgId != null) {
            img_icon.setImageResource(imgId)
        }
        text_desc.text = setDesc(category)

        // 데이터 불러오기
        category?.let { loadData(it) }

        btn_back.setOnClickListener {
            val recommendFragment = RecommendFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.main_content, recommendFragment)?.commit()
        }

        recommend_detail = view.findViewById(R.id.recyclerview_recommend_detail)
        recommend_detail.adapter = RecyclerViewAdapter()
        recommend_detail.layoutManager = LinearLayoutManager(activity)

        return view
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend_detail, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun getItemCount(): Int {
            return plantlist.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var img = viewHolder.findViewById<ImageView>(R.id.img_recommend)
            var species = viewHolder.findViewById<TextView>(R.id.text_species_recommend)
            var tip = viewHolder.findViewById<TextView>(R.id.text_tip_recommend)

            // 리사이클러뷰 아이템 정보
            species.text = plantlist!![position].species.toString()
            tip.text = plantlist!![position].tip
            // 이미지 설정
            var imgId = setImage(species.text.toString())
            if (imgId != null) {
                img.setImageResource(imgId)
            }
        }
    }

    // 파이어베이스에서 데이터 불러오는 함수
    fun loadData(category : String) {
        if (user != null) {
            firestore?.collection("plants")?.whereEqualTo("category", category)
                ?.addSnapshotListener { value, error ->
                    plantlist.clear()
                    for (snapshot in value!!.documents) {
                        var item = snapshot.toObject(Plants::class.java)
                        if (item != null) {
                            plantlist.add(item)
                        }
                    }
                    recommend_detail.adapter?.notifyDataSetChanged()
                }
        }
    }
}