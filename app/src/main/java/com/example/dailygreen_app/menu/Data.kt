package com.example.dailygreen_app.menu

import java.util.*

// 내가 키우는 식물 리스트
data class MyList(
    var name : String? = null,
    var species : String? = null,
    var date: String? = null
)

// 식물 데이터
data class Plants(
    var species : String? = null,
    var name: String? = null,
    var sun : String? = null,
    var temperature : String? = null,
    var water : String? = null,
    var tip : String? = null,
    var from : String? = null,
    var tag : String? = null,
    var s_name : String? = null
)

// 알람 데이터
data class Alarm(
    var name : String? = null,
    var time : String? = null,
    var date : String? = null
)