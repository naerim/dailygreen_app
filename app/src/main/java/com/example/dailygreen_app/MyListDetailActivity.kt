package com.example.dailygreen_app

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.dailygreen_app.menu.MyList
import com.example.dailygreen_app.menu.Plants
import com.example.dailygreen_app.menu.setImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class MyListDetailActivity : AppCompatActivity() {
    lateinit var text_plantname_mylist_detail : TextView
    lateinit var img_mylist_detail : ImageView
    lateinit var text_species_mylist_detail : TextView
    lateinit var text_date_mylist_detail : TextView
    lateinit var text_tip_mylist_detail : TextView
    lateinit var btn_delete_mylist : Button
    lateinit var btn_edit_mylist : Button
    lateinit var spinner : Spinner
    lateinit var edt_date : EditText
    lateinit var btn_date : Button
    lateinit var edt_name : EditText

    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null
    var name : String? = null
    var species : String? = null
    var date : String? = null
    var id : String? = null
    lateinit var mylist: ArrayList<MyList>
    lateinit var itemInfo : ArrayList<Plants>
    lateinit var plantlist: ArrayList<String>

    lateinit var select_species : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_detail)

        text_plantname_mylist_detail = findViewById(R.id.text_plantname_mylist_detail)
        img_mylist_detail = findViewById(R.id.img_mylist_detail)
        text_species_mylist_detail = findViewById(R.id.text_species_mylist_detail)
        text_date_mylist_detail = findViewById(R.id.text_date_mylist_detail)
        text_tip_mylist_detail = findViewById(R.id.text_tip_mylist_detail)
        btn_delete_mylist = findViewById(R.id.btn_delete_mylist)
        btn_edit_mylist = findViewById(R.id.btn_edit_mylist)

        mylist = arrayListOf<MyList>()
        itemInfo = arrayListOf<Plants>()
        plantlist = arrayListOf<String>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // intent와 연결
        name = intent.getStringExtra("name")
        species = intent.getStringExtra("species")
        date = intent.getStringExtra("date")
        id = intent.getStringExtra("id")
        text_plantname_mylist_detail.text = name
        text_species_mylist_detail.text = species
        text_date_mylist_detail.text = date

        // 이미지뷰 설정
        val imgId = setImage(species)
        if (imgId != null) {
            img_mylist_detail.setImageResource(imgId)
        }

        loadData()

        // 나의 리스트에서 삭제
        btn_delete_mylist.setOnClickListener {
            showDeleteDialog()
        }




//        firestore?.collection("users")?.document(user!!.uid)?.collection("mylist")
//            ?.whereEqualTo("name", "$name")
//            ?.addSnapshotListener { value, error ->
//                mylist.clear()
//                for (snapshot in value!!.documents){
//                    var item = snapshot.toObject(MyList::class.java)
//                    if (item != null) {
//                        mylist.add(item)
//                        text_species_mylist_detail.text = mylist[0].species
//                    }
//                }
//            }

    }

    override fun onResume() {
        super.onResume()
        // 나의 리스트 수정
        btn_edit_mylist.setOnClickListener {
            showEditDialog()
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

    fun loadData(){
        // 식물 종에 해당하는 tip 불러오기
        firestore?.collection("plants")?.whereEqualTo("species", "$species")
            ?.addSnapshotListener { value, error ->
                itemInfo.clear()
                for (snapshot in value!!.documents){
                    var item = snapshot.toObject(Plants::class.java)
                    if (item != null) {
                        itemInfo.add(item)
                        text_tip_mylist_detail.text = itemInfo[0].tip
                    }
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

    fun showDeleteDialog(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete_mylist, null)

        val alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("확인") {dialog, which ->
                firestore?.collection("users")?.document(user!!.uid)?.collection("mylist")
                    ?.document("$id")
                    ?.delete()
                    ?.addOnSuccessListener {
                        finish()
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {}
            }
            .setNegativeButton("취소", null)
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    fun showEditDialog(){
        // 다이얼로그
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_addmylist, null)

        spinner = dialogView.findViewById<Spinner>(R.id.spinner)!!
        edt_date = dialogView.findViewById(R.id.edt_date)
        btn_date = dialogView.findViewById(R.id.btn_date)
        edt_name = dialogView.findViewById(R.id.edt_name)

        btn_date.setOnClickListener {
            showDate()
        }

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, plantlist)
        spinner.onItemSelectedListener = MySpinnerListener()

        builder.setView(dialogView)
            .setPositiveButton("확인"){ dialogInterFace, i ->
                // 데이터값이 모두 존재하면 수정
                if (edt_date.text.toString() != ""  && edt_name.text.toString() !=""){
                    if (user != null) {
                        firestore?.collection("users")?.document(user!!.uid)?.collection("mylist")
                            ?.document("$id")
                            ?.update(hashMapOf("date" to edt_date.text.toString(), "species" to select_species, "name" to edt_name.text.toString()) as Map<String, Any>)
                            ?.addOnSuccessListener{
                                finish()
                                Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                            ?.addOnFailureListener{}
                    }
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    fun showDate(){
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var pickdate = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            edt_date.setText("" + year + "." + (month+1) + "." + day)
        }, year, month, day)

        pickdate.show()
    }
}