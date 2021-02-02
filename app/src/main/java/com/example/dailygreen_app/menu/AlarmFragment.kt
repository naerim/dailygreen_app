package com.example.dailygreen_app.menu

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.style.UpdateAppearance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dailygreen_app.R
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class AlarmFragment : Fragment(){

    lateinit var alarmManager: AlarmManager
    var dialog : DatePickerDialog? = null

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        // 알람 관리자 소환
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var textView = view.findViewById<TextView>(R.id.textView2)
//       val textView: TextView = view.findViewById(R.id.textView2)
//
//        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        //val textView: TextView  = view.findViewById(R.id.show_date)
        //textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        val btnAddAlarm = view.findViewById<Button>(R.id.add_alarm)

        btnAddAlarm.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            dialog = DatePickerDialog(activity, year, month, day)
//            dialog = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
//                textView.text = "${i}년 ${i2 + 1}월 ${i3}일일"
//            }
//        }


//        fun clickAddalarm(view:View){
//            // 특정 날짜에 알람 설정하기
//
//            // 날짜 선택 다이얼로그 보이기
//            //val calendar = GregorianCalendar(Locale.KOREA)
//            var calendar = Calendar.getInstance()
//            var year = calendar.get(Calendar.YEAR)
//            var month = calendar.get(Calendar.MONTH)
//            var day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            var listener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
//                textView.text = "${i}년 ${i2+1}월 ${i3}일일"
//            }
//        }

        return view
    }
}