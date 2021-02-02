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
import com.example.dailygreen_app.R
import java.util.*

class AlarmFragment : Fragment(){

    lateinit var alarmManager: AlarmManager
//    var dialog : DatePickerDialog? = null

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        // 알람 관리자 소환
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var textDate: TextView = view.findViewById(R.id.text_date)
        var textTime: TextView = view.findViewById(R.id.text_time)

        var btnAddAlarm = view.findViewById<Button>(R.id.add_alarm)


        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        // 선택 버튼 눌렀을 때
        btnAddAlarm.setOnClickListener {
            // 시간 선택
            var picktime = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                textTime.setText("" + hour + "시 " + minute + "분  ")
            }, hour, minute, true)
            picktime.show()
            // 날짜 선택
            var pickdate = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            textDate.setText("" + day + "/ " + month+1 + "/ " + year)
            }, year, month, day)

            pickdate.show()
        }
        return view
    }


}