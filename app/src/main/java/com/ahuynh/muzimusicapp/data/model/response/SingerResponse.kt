package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Singer

data class SingerResponse(
    val id: Long,
    val name: String,
    val avatar: String,


    ) {
    fun toSinger(): Singer {
        return Singer(
            id = this.id,
            name = this.name,
            avatar = this.avatar
        )
    }
}

data class SingerResponseData(
    val message: String,
    val data: List<SingerResponse>
)


fun List<SingerResponse>.toListSinger(): List<Singer> {
    return map { it.toSinger() }
}
