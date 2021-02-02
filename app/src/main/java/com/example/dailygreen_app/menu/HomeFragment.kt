package com.example.dailygreen_app.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dailygreen_app.R
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(){

    var firestore : FirebaseFirestore? = null
    lateinit var recyclerview : RecyclerView
    lateinit var plant: ArrayList<Plant>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)

        plant = arrayListOf<Plant>()

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 파이어베이스에서 값 불러오기
        firestore?.collection("plants")?.addSnapshotListener { value, error ->
            plant.clear()
            for (snapshot in value!!.documents){
                var item = snapshot.toObject(Plant::class.java)
                if (item != null) {
                    plant.add(item)
                }
            }
            recyclerview.adapter?.notifyDataSetChanged()
        }

        recyclerview = view.findViewById(R.id.recyclerview)
        recyclerview.adapter = RecyclerViewAdapter()
        recyclerview.layoutManager = LinearLayoutManager(activity)

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

            name.text = plant!![position].name
            species.text = plant!![position].species
        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return plant.size
        }
    }
}
