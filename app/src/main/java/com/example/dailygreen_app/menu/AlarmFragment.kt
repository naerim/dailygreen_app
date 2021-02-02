package com.example.dailygreen_app.menu

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dailygreen_app.R

class AlarmFragment : Fragment(){
    lateinit var set_alarm : Button
    lateinit var set_textView : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_alarm, container, false)

        set_alarm = view.findViewById(R.id.set_alarm)
        set_textView = view.findViewById(R.id.set_textView)

        set_alarm.setOnClickListener {
            showDialog()
        }

        return view
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.alarm_dialog, null)
        val dialogText = dialogView.findViewById<TextView>(R.id.textView_test)

        builder.setView(dialogView)
            .setPositiveButton("확인"){ dialogInterFace, i ->
                set_textView.text = dialogText.text
            }
            .setNegativeButton("취소", null)
            .show()
    }
}