package com.example.dailygreen_app.menu

import android.os.Bundle
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
    var recyclerview : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

//        recyclerview = getView()?.findViewById(R.id.recyclerview)
        recyclerview = view.findViewById(R.id.recyclerview!!) as RecyclerView

        recyclerview?.adapter = RecyclerViewAdapter()
        recyclerview?.layoutManager = LinearLayoutManager(activity)

        return view
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>(){

        var plant : ArrayList<Plant> = arrayListOf()

        init {
            firestore?.collection("plants")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // 배열 비워줌
                plant.clear()
                for (snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(Plant::class.java)
                    plant.add(item!!)
                }
                notifyDataSetChanged()
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.home_item_layout, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return plant.size
        }

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

    }
}