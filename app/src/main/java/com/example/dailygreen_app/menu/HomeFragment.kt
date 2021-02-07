package com.example.dailygreen_app.menu

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dailygreen_app.MyListDetailActivity
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class HomeFragment : Fragment(){
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null

    lateinit var recyclerview_home : RecyclerView
    lateinit var mylist: ArrayList<MyList>
    lateinit var plantlist : ArrayList<String>

    lateinit var btn_addPlant : Button
    lateinit var spinner : Spinner
    lateinit var edt_date : EditText
    lateinit var btn_date : Button
    lateinit var edt_name : EditText

    lateinit var select_species : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)

        mylist = arrayListOf<MyList>()
        plantlist = arrayListOf<String>()
        btn_addPlant = view.findViewById(R.id.btn_addPlant)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 파이어베이스에서 값 불러오기
        loadData()

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

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        //  view와 실제 데이터 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var name : TextView
            var species : TextView
            var img_mylist : ImageView

            name = viewHolder.findViewById(R.id.name)
            species = viewHolder.findViewById(R.id.species)
            img_mylist = viewHolder.findViewById(R.id.img_mylist)

            // 리사이클러뷰 아이템 정보
            name.text = mylist!![position].name
            species.text = mylist!![position].species
            var date : String = mylist[position].date.toString()
            var id : String? = mylist[position].id

            // 이미지 설정
            var imgId = setImage(species.text as String?)
            if (imgId != null) {
                img_mylist.setImageResource(imgId)
            }

            // 클릭이벤트(디테일뷰로 넘어감)
            viewHolder.setOnClickListener {
                val intent = Intent(viewHolder?.context, MyListDetailActivity::class.java)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("species", species.text.toString())
                intent.putExtra("date", date)
                intent.putExtra("id", id)
                ContextCompat.startActivity(viewHolder.context, intent, null)
                Toast.makeText(viewHolder.context,"성공", Toast.LENGTH_SHORT).show()
            }
        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return mylist.size
        }

    }

    // 스피너 사용
    inner class MySpinnerListener : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            select_species = plantlist[position]
        }

    }

    // 다이얼로그
    fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_addmylist, null)

        spinner = dialogView.findViewById<Spinner>(R.id.spinner)!!
        edt_date = dialogView.findViewById(R.id.edt_date)
        btn_date = dialogView.findViewById(R.id.btn_date)
        edt_name = dialogView.findViewById(R.id.edt_name)

        btn_date.setOnClickListener {
            showDate()
        }

        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, plantlist)
        spinner.onItemSelectedListener = MySpinnerListener()

        builder.setView(dialogView)
            .setPositiveButton("등록"){ dialogInterFace, i ->
                // 데이터값이 모두 존재하면 추가
                if (edt_date.text.toString() != ""  && edt_name.text.toString() !=""){
                    if (user != null) {
                        // 랜덤 아이디
                        val random = Random()
                        val id : Int = random.nextInt(1000)
                        val idString : String = id.toString()

                        firestore?.collection("users")?.document(user!!.uid)?.collection("mylist")
                            ?.document("$idString")
                            ?.set(hashMapOf("id" to idString, "date" to edt_date.text.toString(), "species" to select_species, "name" to edt_name.text.toString()))
                            ?.addOnSuccessListener{}
                            ?.addOnFailureListener{}
                    }
                    recyclerview_home.adapter?.notifyDataSetChanged()
                }
            }
            .setNegativeButton("취소", null)
            .show()
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
                    recyclerview_home.adapter?.notifyDataSetChanged()
                }
        }

        // 앱에 저장되어 있는 식물 종류 불러오기
        firestore?.collection("plants")?.addSnapshotListener { value, error ->
            for (snapshot in value!!.documents){
                var item = snapshot.toObject(Plants::class.java)
                if (item != null) {
                    item.species?.let { plantlist.add(it) }
                }
            }
        }
    }

    fun showDate(){
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var pickdate = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            edt_date.setText("" + year + "." + (month+1) + "." + day)
        }, year, month, day)

        pickdate.show()
    }

}
