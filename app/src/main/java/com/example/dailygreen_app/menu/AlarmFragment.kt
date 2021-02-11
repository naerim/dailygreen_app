package com.example.dailygreen_app.menu

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class AlarmFragment : Fragment(){

    var firestore : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var user : FirebaseUser? = null
    lateinit var recyclerview_alarm : RecyclerView
    lateinit var myalarmlist : ArrayList<Alarm>
    lateinit var myplantlist : ArrayList<String>

    lateinit var btn_checkdate : Button
    lateinit var btn_addalarm : Button
    lateinit var calendar : GregorianCalendar
    lateinit var text_time : TextView
    lateinit var text_date : TextView
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
        text_date = view.findViewById(R.id.text_date)
        recyclerview_alarm = view.findViewById(R.id.recyclerview_alarm)
        selectspinner = view.findViewById(R.id.spinner_alarmpick)

        // 파이어베이스 인증 객체
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
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

        btn_checkdate.setOnClickListener {
            pick_date = view!!.findViewById(R.id.text_date)
            pick_time = view!!.findViewById(R.id.text_time)
            checkAlarm()
        }

        btn_addalarm.setOnClickListener {
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
            var btn_delete_alarm : Button
            var name : TextView

            date = viewHolder.findViewById(R.id.text_showdate)
            time = viewHolder.findViewById(R.id.text_showtime)
            btn_delete_alarm = viewHolder.findViewById(R.id.btn_delete_alarm)
            name = viewHolder.findViewById(R.id.text_showname)

            time.text = myalarmlist!![position].time
            date.text = myalarmlist!![position].date
            var id = myalarmlist!![position].id

            // 알람 삭제
            btn_delete_alarm.setOnClickListener {
                if (id != null) {
                    showDeleteDialog(id)
                }
            }
            name.text = myalarmlist!![position].name

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
        }, hour, minute, false)
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

    // DB에 알람 추가
    fun addAlarm(id : Int)
    {
        val idString = id.toString()

        firestore?.collection("users")?.document(user!!.uid)?.collection("alarm")
            ?.document("$idString")
            ?.set(hashMapOf("id" to idString, "name" to select_plant, "time" to pick_time.text.toString(), "date" to pick_date.text.toString()))
            ?.addOnSuccessListener {}
            ?.addOnFailureListener { }
        recyclerview_alarm.adapter?.notifyDataSetChanged()
    }

    // 기기에 알람 설정
    fun setAlarm(){
        val random = Random()
        val alarmid : Int = random.nextInt(1000)

        var setcalendar = GregorianCalendar(testyear, testmonth, testday, testhour, testmin)
        val intent = Intent(getActivity(), ShowalarmActivity::class.java)
        intent.putExtra("id", alarmid.toString())
        val pendingIntent = PendingIntent.getActivity(context, alarmid, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setcalendar.timeInMillis, pendingIntent)
        } else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, setcalendar.timeInMillis, pendingIntent)
        }
        addAlarm(alarmid)
        text_date.setText("Date")
        text_time.setText("Time")
    }

    fun loadData(){
        // 파이어베이스에서 알람 리스트 불러오기
        firestore?.collection("users")?.document(user!!.uid)?.collection("alarm")
            ?.addSnapshotListener { value, error ->
                myalarmlist.clear()
                for(snapshot in value!!.documents){
                    var item = snapshot.toObject(Alarm::class.java)
                    if(item != null)
                        myalarmlist.add(item)
            }
            recyclerview_alarm.adapter?.notifyDataSetChanged()
        }

        // 파이어베이스에서 내 리스트 값 불러오기
        firestore?.collection("users")?.document(user!!.uid)?.collection("mylist")
            ?.addSnapshotListener { value, error ->
                for(snapshot in value!!.documents){
                    var item = snapshot.toObject(MyList::class.java)
                    if(item != null)
                        item.name?.let { myplantlist.add(it) }
                }
            spinnerAdapter.notifyDataSetChanged()
        }
    }

    fun showDeleteDialog(id : String){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_mylist, null)
        var alid = id.toInt()

        builder.setView(dialogView)
            .setPositiveButton("확인"){ dialogInterFace, i ->
                firestore?.collection("users")?.document(user!!.uid)?.collection("alarm")
                    ?.document(id)
                    ?.delete()
                    ?.addOnSuccessListener {
                        Toast.makeText(activity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        // 기기 내부의 알람 지우기
                        val intent = Intent(getActivity(), ShowalarmActivity::class.java)
                        val pendingIntent = PendingIntent.getActivity(context, alid, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                        alarmManager.cancel(pendingIntent)
                    }
                    ?.addOnFailureListener {}
            }
            .setNegativeButton("취소", null)
            .show()
    }

}