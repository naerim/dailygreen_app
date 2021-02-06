package com.example.dailygreen_app.menu

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.AlarmManagerCompat.setExactAndAllowWhileIdle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.R
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min
import kotlin.properties.Delegates

class AlarmFragment : Fragment(){

    var firestore : FirebaseFirestore? = null
    lateinit var recyclerview_alarm : RecyclerView
    lateinit var myalarmlist : ArrayList<Alarm>
    lateinit var myplantlist : ArrayList<String>

    lateinit var btn_checkdate : Button
    lateinit var btn_addalarm : Button
    lateinit var calendar : GregorianCalendar
    lateinit var text_time : TextView
    lateinit var selectspinner: Spinner
    lateinit var alarmManager: AlarmManager

    lateinit var pick_time : TextView
    lateinit var pick_date : TextView

    lateinit var spinnerAdapter : ArrayAdapter<String>
    lateinit var select_plant : String

    // 알람 셋팅
    var testhour by Delegates.notNull<Int>()
    var testmin by Delegates.notNull<Int>()
    var testday by Delegates.notNull<Int>()
    var testyear by Delegates.notNull<Int>()
    var testmonth by Delegates.notNull<Int>()

    var alarmid by Delegates.notNull<Int>()


    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        alarmid = 0
        myalarmlist = arrayListOf<Alarm>()
        myplantlist = arrayListOf<String>()
        btn_checkdate = view.findViewById(R.id.btn_checkdate)
        btn_addalarm = view.findViewById(R.id.btn_addalarm)
        text_time = view.findViewById(R.id.text_time)
        recyclerview_alarm = view.findViewById(R.id.recyclerview_alarm)
        selectspinner = view.findViewById(R.id.spinner_alarmpick)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // 스피너 어댑터
        spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, myplantlist)
        // 알람 관리자 소환
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // 알람 설정
        calendar = Calendar.getInstance() as GregorianCalendar

        // 파이어베이스에서 데이터 불러오기
        loadData()

        // 스피너
        selectspinner.adapter = spinnerAdapter
        selectspinner.onItemSelectedListener = AlarmSpinnerListener()
        // 리사이클러
        recyclerview_alarm.adapter = RecyclerViewAdapter()
        recyclerview_alarm.layoutManager = LinearLayoutManager(activity)

        // 알람등록
//        year = calendar.get(Calendar.YEAR)
//        month = calendar.get(Calendar.MONTH)
//        day = calendar.get(Calendar.DAY_OF_MONTH)
//        hour = calendar.get(Calendar.HOUR)
//        minute = calendar.get(Calendar.MINUTE)


        btn_checkdate.setOnClickListener {
            pick_date = view!!.findViewById(R.id.text_date)
            pick_time = view!!.findViewById(R.id.text_time)
            checkAlarm()
        }

        btn_addalarm.setOnClickListener {
            addAlarm()
            setAlarm()
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
            select_plant = myplantlist[position]
        }
    }

    fun checkAlarm(){
        // 시간 선택
//        textDate = view!!.findViewById(R.id.text_date)
//        textTime = view!!.findViewById(R.id.text_time)

        calendar = Calendar.getInstance() as GregorianCalendar
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        var picktime = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            pick_time.setText("" + hourOfDay + " : " + minute)
            testhour = hourOfDay
            testmin = minute
        }, hour, minute, true)
        picktime.show()
        // 날짜 선택
        var pickdate = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            pick_date.setText("" + year + ". " +  (month+1) + ". " + day)
            testday = day
            testyear = year
            testmonth = month
        }, year, month, day)
        pickdate.show()


    }

    fun addAlarm()
    {
        firestore?.collection("alarm")
            ?.add(hashMapOf("name" to select_plant, "time" to pick_time.text.toString(), "date" to pick_date.text.toString()))
            ?.addOnSuccessListener { }
            ?.addOnFailureListener { }
        recyclerview_alarm.adapter?.notifyDataSetChanged()
//
//        Toast.makeText(context, "setalarm실행   "+ testhour + "시" + testmin, Toast.LENGTH_LONG).show()
//        Toast.makeText(context, "이번엔 과연 일이 " + testday, Toast.LENGTH_LONG).show()
    }

      fun setAlarm(){
          alarmid++
//        var inputtime = text_time.toString()
//        var token = inputtime.split(":")
//        hour = token[0].toInt()
//        minute = token[1].toInt()
//        Toast.makeText(context, "setalarm실행   "+ testhour + "시" + testmin, Toast.LENGTH_LONG).show()
        //var inputdate = pick_date.toString()
        var setcalendar = GregorianCalendar(testyear, testmonth, testday, testhour, testmin)
          Toast.makeText(context, "setalarm실행   "+ testhour + "시" + testmin, Toast.LENGTH_LONG).show()
        val intent = Intent(getActivity(), ShowalarmActivity::class.java)
        //startActivity(intent)
        val pendingIntent = PendingIntent.getActivity(context, alarmid, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setcalendar.timeInMillis, pendingIntent)
            Toast.makeText(context, "버전1 실행", Toast.LENGTH_SHORT).show()
        } else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, setcalendar.timeInMillis, pendingIntent)
            Toast.makeText(context, "버전2 실행", Toast.LENGTH_SHORT).show()
        }
    }

//    fun setAlarm(context: Context){
//        //val setcalendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, hour)
//        calendar.set(Calendar.MINUTE, minute)
//        calendar.set(Calendar.YEAR, year)
//        calendar.set(Calendar.MONTH, month)
//        calendar.set(Calendar.DAY_OF_MONTH,day)
//
//        var intent = Intent(context, MAl::class.java)
//
//    }

    fun loadData(){
        // 파이어베이스에서 알람 리스트 불러오기
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
            for(snapshot in value!!.documents){
                var item = snapshot.toObject(MyList::class.java)
                if(item != null)
                    item.name?.let { myplantlist.add(it) }
            }
            spinnerAdapter.notifyDataSetChanged()
        }
    }

//    fun showDialog(){
//        val builder = AlertDialog.Builder(activity)
//        val dialogView = layoutInflater.inflate(R.layout.dialog_alarm, null)
//        val dialogText = dialogView.findViewById<TextView>(R.id.textView_test)
//
//        builder.setView(dialogView)
//            .setPositiveButton("확인"){ dialogInterFace, i ->
//            }
//            .setNegativeButton("취소", null)
//            .show()
//    }

}