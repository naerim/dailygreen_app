package com.example.dailygreen_app.menu

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dailygreen_app.Alarm
import com.example.dailygreen_app.R
import java.util.*
import kotlin.collections.ArrayList

class AlarmFragment : Fragment(){

    lateinit var alarmManager: AlarmManager
    lateinit var alarm: ArrayList<Alarm>

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        // 알람 관리자 소환
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var textDate: TextView = view.findViewById(R.id.text_date)
        var textTime: TextView = view.findViewById(R.id.text_time)

        var btnAddAlarm = view.findViewById<Button>(R.id.btn_addalarm)


        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        // 선택 버튼 눌렀을 때
        btnAddAlarm.setOnClickListener {
            // 시간 선택
            //var timeSetListener = TimePickerDialog.OnTimeSetListener(calendar.get(Calendar.HOUR_OF_DAY))

            var picktime = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                textTime.setText("" + hourOfDay + "시 " + minute + "분  ")
            }, hour, minute, true)
            picktime.show()
            // 날짜 선택
            var pickdate = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            textDate.setText("" + day + "/ " +  (month+1) + "/ " + year)
            }, year, month, day)

            pickdate.show()

            var calendar = GregorianCalendar(year, month, day, hour, minute)
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

        }

        // 리사이클러뷰의 아이템 총 개수
        override fun getItemCount(): Int {
            return alarm.size
        }



    }

}