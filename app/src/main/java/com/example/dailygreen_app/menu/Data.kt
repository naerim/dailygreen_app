package com.example.dailygreen_app.menu

import com.example.dailygreen_app.R
import java.lang.Exception

// 내가 키우는 식물 리스트
data class MyList(
    var name : String? = null,
    var species : String? = null,
    var date: String? = null,
    var id: String? = null
)

// 식물 데이터
data class Plants(
    var species : String? = null,
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

var ImageList = hashMapOf(
    "고무나무" to "plant_gomu",
    "호야" to "plant_hoya"
)

// 종에 해당하는 이미지 찾아서 아이디값 반환
fun setImage(species: String?): Int? {
    var imgName = ImageList["$species"]
    var resId = imgName?.let { R.drawable::class.java.getId(it) }
    return resId
}

inline fun <reified T : Class<R.drawable>> T.getId(resourceName: String): Int {
    return try{
        val idField = getDeclaredField(resourceName)
        idField.getInt(idField)
    } catch (e:Exception){
        e.printStackTrace()
        -1
    }
}


