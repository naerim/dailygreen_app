package com.example.dailygreen_app.menu

import android.widget.TextView
import com.example.dailygreen_app.R
import java.lang.Exception
import java.util.*

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
    var tip : String? = null,
    var category: String? = null
)

// 알람 데이터
data class Alarm(
    var name : String? = null,
    var time : String? = null,
    var date : String? = null,
    var id : String? = null
)

// 다이어리 데이터
data class Diary(
    var id : String? = null,
    var category : String? = null,
    var date : String? = null,
    var content : String? = null

)
// 이미지 연결
var ImageList = hashMapOf(
    "관음죽" to "plant_jook",
    "인도고무나무" to "plant_indogomu",
    "아이비" to "plant_ivy",
    "아레카야자" to "plant_arekayaja",
    "백리향" to "plant_backrihyang",
    "바질" to "plant_bazil",
    "알로카시아" to "plant_allo",
    "크루시아" to "plant_crew",
    "블루스타" to "plant_blue",
    "구문초" to "plant_goo",
    "긴잎끈끈이주걱" to "plant_longleaf",
    "벤쿠버제라늄" to "plant_ven",
    "돈나무" to "plant_done",
    "청기린" to "plant_chung",
    "코코키아그린" to "plant_coro",
    "유칼립투스" to "plant_you",
    "무늬아비스" to "plant_muni",
    "켄차야자" to "plant_cancha"
)

// 종에 해당하는 이미지 찾아서 아이디값 반환
fun setImage(species: String?): Int? {
    var imgName = ImageList["$species"]
    var resId = imgName?.let { R.drawable::class.java.getId(it) }
    return resId
}

// 아이콘 연결
var IconList = hashMapOf(
    "공기정화" to "icon_fresh",
    "반려동물" to "icon_pet",
    "물조금만" to "icon_dry",
    "해충박멸" to "icon_killbug",
    "햇빛조금" to "icon_dark",
    "인테리어" to "icon_interior"
)

fun setIcon(category: String?): Int? {
    var iconName = IconList["$category"]
    var resId = iconName?.let {R.drawable::class.java.getId(it)}
    return resId
}

// desc 연결
var descList = hashMapOf(
    "공기정화" to "공기를 정화해주는 식물들",
    "반려동물" to "반려동물에게 해가 가지 않는 식물들",
    "물조금만" to "물을 조금만 해도 되는 식물들",
    "해충박멸" to "벌레들이 싫어하는 식물들",
    "햇빛조금" to "어두운 데서도 잘 자라는 식물들",
    "인테리어" to "집을 꾸밀 수 있는 식물들"
)

fun setDesc(category: String?): CharSequence? {
    var desc = descList["$category"]
    return desc
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


