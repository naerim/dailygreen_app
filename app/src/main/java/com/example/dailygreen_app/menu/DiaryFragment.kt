package com.example.dailygreen_app.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class DiaryFragment : Fragment(){
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null

    lateinit var recyclerview_diary_home : RecyclerView
    lateinit var mylist: ArrayList<MyList>
//    lateinit var text_diary_home_species : TextView
//    lateinit var text_diary_home_name : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_diary, container, false)

        mylist = arrayListOf<MyList>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 파이어베이스에서 값 불러오기
        loadData()

        recyclerview_diary_home = view.findViewById(R.id.recyclerview_diary_home)
        recyclerview_diary_home.adapter = RecyclerViewAdapter()
        recyclerview_diary_home.layoutManager = GridLayoutManager(activity, 2)


        return view
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary_mylist, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        //  view와 실제 데이터 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var name : TextView
            var species : TextView
            var img_mylist : ImageView

            name = viewHolder.findViewById(R.id.text_diary_home_name)
            species = viewHolder.findViewById(R.id.text_diary_home_species)
            img_mylist = viewHolder.findViewById(R.id.img_diary_home)

            // 리사이클러뷰 아이템 정보
            name.text = mylist!![position].name
            species.text = mylist!![position].species

            // 이미지 설정
            var imgId = setImage(species.text as String?)
            if (imgId != null) {
                img_mylist.setImageResource(imgId)
            }

            // 클릭이벤트
            viewHolder.setOnClickListener {
                val listDiaryFragment = ListDiaryFragment()
                val bundle = Bundle()
                bundle.putString("name", name.text.toString())
                listDiaryFragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.main_content, listDiaryFragment)
                    ?.commit()
            }
        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return mylist.size
        }

    }

    // 파이어베이스에서 데이터 불러오는 함수
    fun loadData(){
        // 키우는 식물 리스트 불러오기
        if (user != null) {
            firestore?.collection("users")?.document(user!!.uid)
                ?.collection("mylist")?.orderBy("date", Query.Direction.DESCENDING)
                ?.addSnapshotListener { value, error ->
                    mylist.clear()
                    for (snapshot in value!!.documents){
                        var item = snapshot.toObject(MyList::class.java)
                        if (item != null) {
                            mylist.add(item)
                        }
                    }
                    recyclerview_diary_home.adapter?.notifyDataSetChanged()
                }
        }
    }
}