package com.example.dailygreen_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.dailygreen_app.menu.MyList
import com.example.dailygreen_app.menu.Plants
import com.example.dailygreen_app.menu.setImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class MyListDetailActivity : AppCompatActivity() {
    lateinit var text_plantname_mylist_detail : TextView
    lateinit var img_mylist_detail : ImageView
    lateinit var text_species_mylist_detail : TextView
    lateinit var text_date_mylist_detail : TextView
    lateinit var text_tip_mylist_detail : TextView

    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    var user : FirebaseUser? = null
    var name : String? = null
    var species : String? = null
    var date : String? = null
    lateinit var mylist: ArrayList<MyList>
    lateinit var plantlist: ArrayList<Plants>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_detail)

        text_plantname_mylist_detail = findViewById(R.id.text_plantname_mylist_detail)
        img_mylist_detail = findViewById(R.id.img_mylist_detail)
        text_species_mylist_detail = findViewById(R.id.text_species_mylist_detail)
        text_date_mylist_detail = findViewById(R.id.text_date_mylist_detail)
        text_tip_mylist_detail = findViewById(R.id.text_tip_mylist_detail)

        mylist = arrayListOf<MyList>()
        plantlist = arrayListOf<Plants>()

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // intent와 연결
        name = intent.getStringExtra("name")
        species = intent.getStringExtra("species")
        date = intent.getStringExtra("date")
        text_plantname_mylist_detail.text = name
        text_species_mylist_detail.text = species
        text_date_mylist_detail.text = date

        // 이미지뷰 설정
        var imgId = setImage(species)
        if (imgId != null) {
            img_mylist_detail.setImageResource(imgId)
        }


        firestore?.collection("plants")?.whereEqualTo("species", "$species")
            ?.addSnapshotListener { value, error ->
                plantlist.clear()
                for (snapshot in value!!.documents){
                    var item = snapshot.toObject(Plants::class.java)
                    if (item != null) {
                        plantlist.add(item)
                        text_tip_mylist_detail.text = plantlist[0].tip
                    }
                }
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
}
