package com.example.dailygreen_app.menu

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dailygreen_app.R
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(){

    var firestore : FirebaseFirestore? = null
    lateinit var recyclerview_home : RecyclerView
    lateinit var mylist: ArrayList<MyList>
    lateinit var btn_addPlant : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)

        mylist = arrayListOf<MyList>()
        btn_addPlant = view.findViewById(R.id.btn_addPlant)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 파이어베이스에서 값 불러오기
        firestore?.collection("mylist")?.addSnapshotListener { value, error ->
            mylist.clear()
            for (snapshot in value!!.documents){
                var item = snapshot.toObject(MyList::class.java)
                if (item != null) {
                    mylist.add(item)
                }
            }
            recyclerview_home.adapter?.notifyDataSetChanged()
        }

        recyclerview_home = view.findViewById(R.id.recyclerview_home)
        recyclerview_home.adapter = RecyclerViewAdapter()
        recyclerview_home.layoutManager = LinearLayoutManager(activity)

        // 키우는 식물 추가
        btn_addPlant.setOnClickListener {
            showDialog()
        }

        return view
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_myplantlist, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        //  view와 실제 데이터 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var name : TextView
            var species : TextView

            name = viewHolder.findViewById(R.id.name)
            species = viewHolder.findViewById(R.id.species)

            name.text = mylist!![position].name
            species.text = mylist!![position].species
        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return mylist.size
        }
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_addplants, null)

        builder.setView(dialogView)
            .setPositiveButton("등록"){ dialogInterFace, i ->

            }
            .setNegativeButton("취소", null)
            .show()
    }
}
