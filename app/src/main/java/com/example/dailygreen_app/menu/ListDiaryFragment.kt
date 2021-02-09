package com.example.dailygreen_app.menu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.DiaryDetailActivity
import com.example.dailygreen_app.MyListDetailActivity
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class ListDiaryFragment : Fragment(){
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null

    lateinit var recyclerview_diary_detail : RecyclerView
    lateinit var btn_back : Button
    lateinit var btn_addDiary : Button
    lateinit var text_name_diary_list: TextView
    lateinit var text_species_diary_list : TextView
    lateinit var img_diary_list : ImageView

    lateinit var diarylist: ArrayList<Diary>
    var name : String? = null
    var species : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_diary_list, container, false)

        btn_back = view.findViewById(R.id.btn_back)
        btn_addDiary = view.findViewById(R.id.btn_addDiary)
        text_name_diary_list = view.findViewById(R.id.text_name_diary_list)
        text_species_diary_list = view.findViewById(R.id.text_species_diary_list)
        img_diary_list = view.findViewById(R.id.img_diary_list)
        diarylist = arrayListOf<Diary>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // DiaryFragment에서 데이터 전달 받기
        val extra = arguments
        if (extra != null){
            name = extra.getString("name")
            species = extra.getString("species")
        }

        // 파이어베이스에서 값 불러오기
        text_name_diary_list.text = name
        text_species_diary_list.text = species
        loadData(text_name_diary_list.text as String)

        // 이미지 설정
        var imgId = setImage(species)
        if (imgId != null) {
            img_diary_list.setImageResource(imgId)
        }

        btn_back.setOnClickListener {
            val diaryFragment = DiaryFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.main_content, diaryFragment)
                ?.commit()
        }

        btn_addDiary.setOnClickListener {
            showDialog(text_name_diary_list.text as String)
        }

        recyclerview_diary_detail = view.findViewById(R.id.recyclerview_diary_detail)
        recyclerview_diary_detail.adapter = RecyclerViewAdapter()
        recyclerview_diary_detail.layoutManager = LinearLayoutManager(activity)

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
            return diarylist.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var date = viewHolder.findViewById<TextView>(R.id.text_date_diary_detail)
            var content = viewHolder.findViewById<TextView>(R.id.text_content_diary_detail)

            // 리사이클러뷰 아이템 정보
            date.text = diarylist!![position].date.toString()
            content.text = diarylist!![position].content
            var id : String? = diarylist[position].id

            viewHolder.setOnClickListener {
                val intent = Intent(viewHolder?.context, DiaryDetailActivity::class.java)
                intent.putExtra("content", content.text as String)
                intent.putExtra("date", date.text as String)
                intent.putExtra("id", id)
                ContextCompat.startActivity(viewHolder.context, intent, null)
            }
        }

    }

    // 파이어베이스에서 데이터 불러오는 함수
    fun loadData(category : String) {
        if (user != null) {
            firestore?.collection("users")?.document(user!!.uid)
                ?.collection("diary")?.whereEqualTo("category", category)
                ?.addSnapshotListener { value, error ->
                    diarylist.clear()
                    for (snapshot in value!!.documents) {
                        var item = snapshot.toObject(Diary::class.java)
                        if (item != null) {
                            diarylist.add(item)
                        }
                    }
                    recyclerview_diary_detail.adapter?.notifyDataSetChanged()
                }
        }
    }

    fun getDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val getTime = sdf.format(date)
        return getTime
    }

    // 다이얼로그
    fun showDialog(category : String){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_adddiary, null)

        var text_date_diary = dialogView.findViewById<TextView>(R.id.text_date_diary)
        var edt_content_diary = dialogView.findViewById<EditText>(R.id.edt_content_diary)

        text_date_diary.text = getDate()

        builder.setView(dialogView)
            .setPositiveButton("등록"){ dialogInterFace, i ->
                // 데이터값이 모두 존재하면 추가
                if (edt_content_diary.text.toString() != ""){
                    if (user != null) {
                        // 랜덤 아이디
                        val random = Random()
                        val id : Int = random.nextInt(1000)
                        val idString : String = id.toString()

                        firestore?.collection("users")?.document(user!!.uid)?.collection("diary")
                            ?.document("$idString")
                            ?.set(hashMapOf("id" to idString, "date" to text_date_diary.text.toString(), "content" to edt_content_diary.text.toString(), "category" to category))
                            ?.addOnSuccessListener{}
                            ?.addOnFailureListener{}
                    }
                    recyclerview_diary_detail.adapter?.notifyDataSetChanged()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }
}