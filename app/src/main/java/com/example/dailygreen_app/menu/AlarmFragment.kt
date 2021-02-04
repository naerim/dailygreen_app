package com.example.dailygreen_app.menu

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.R
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class AlarmFragment : Fragment(){

    var firestore : FirebaseFirestore? = null
    lateinit var recyclerview_alarm : RecyclerView
    lateinit var myalarmlist : ArrayList<Alarm>
    lateinit var myplantlist : ArrayList<String>

    lateinit var btn_addAlarm : Button
    lateinit var calendar : GregorianCalendar
//    lateinit var text : Text


    lateinit var text_time : TextView
    lateinit var selectspinner: Spinner

    lateinit var alarmManager: AlarmManager

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        // 알람 관리자 소환
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        myalarmlist = arrayListOf<Alarm>()
        btn_addAlarm = view.findViewById(R.id.btn_addalarm)
        text_time = view.findViewById(R.id.text_time)

        myplantlist = arrayListOf<String>()

        recyclerview_alarm = view.findViewById(R.id.recyclerview_alarm)
        selectspinner = view.findViewById(R.id.spinner_alarmpick)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 파이어베이스에서 알람 값 불러오기
        firestore?.collection("alarm")?.addSnapshotListener { value, error ->
            myalarmlist.clear()
            for(snapshot in value!!.documents){
                var item = snapshot.toObject(Alarm::class.java)
                if(item != null)
                    myalarmlist.add(item)
            }
            recyclerview_alarm.adapter?.notifyDataSetChanged()
        }

        // 파이어베이스에서 내 리스트 값 불러오기
        firestore?.collection("mylist")?.addSnapshotListener { value, error ->
            myplantlist.clear()
            for(snapshot in value!!.documents){
                var item = snapshot.toObject(MyList::class.java)
                if(item != null)
                    item.name?.let { myplantlist.add(it) }
            }

        }

        var items = arrayListOf<String>("item1", "item2", "item3")


        selectspinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, items)
        selectspinner.onItemSelectedListener = AlarmSpinnerListener()


        recyclerview_alarm.adapter = RecyclerViewAdapter()
        recyclerview_alarm.layoutManager = LinearLayoutManager(activity)




//        var textDate: TextView = view.findViewById(R.id.text_date)
//        var textTime: TextView = view.findViewById(R.id.text_time)

//        var btnAddAlarm = view.findViewById<Button>(R.id.btn_addalarm)



        // 알람추가 버튼 눌렀을 때
        btn_addAlarm.setOnClickListener {
            addNewAlarm()
        }

        return view
    }

    // 리사이클러뷰 사용
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarmlist, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        // view와 실제 데이터 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            var time : TextView
            var date : TextView

            date = viewHolder.findViewById(R.id.text_showtime)
            time = viewHolder.findViewById(R.id.text_showdate)

            time.text = myalarmlist!![position].time
            date.text = myalarmlist!![position].date

        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return myalarmlist.size
        }
    }

    // 스피너 사용(식물 고르기)
    inner class AlarmSpinnerListener : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        }

    }
//    inner class AlarmSpinnerListener : AdapterView.OnItemSelectedListener{
//        override fun onNothingSelected(parent: AdapterView<*>?) {
//            //test.text = "error error"
//        }
//
//        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
////            text_time.text = myplantlist[position]
//        }
//    }

    fun addNewAlarm(){
        // 시간 선택
        //var timeSetListener = TimePickerDialog.OnTimeSetListener(calendar.get(Calendar.HOUR_OF_DAY))
        var textDate: TextView = view!!.findViewById(R.id.text_date)
        var textTime: TextView = view!!.findViewById(R.id.text_time)

        calendar = Calendar.getInstance() as GregorianCalendar
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        var picktime = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            textTime.setText("" + hourOfDay + "시 " + minute + "분  ")
        }, hour, minute, true)
        picktime.show()
        // 날짜 선택
        var pickdate = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            textDate.setText("" + day + "/ " +  (month+1) + "/ " + year)
        }, year, month, day)
        pickdate.show()

        //var calendar = GregorianCalendar(year, month, day, hour, minute)
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_alarm, null)
        val dialogText = dialogView.findViewById<TextView>(R.id.textView_test)

        builder.setView(dialogView)
            .setPositiveButton("확인"){ dialogInterFace, i ->
            }
            .setNegativeButton("취소", null)
            .show()
    }

}