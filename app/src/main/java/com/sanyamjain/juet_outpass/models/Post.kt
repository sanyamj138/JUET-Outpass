package com.sanyamjain.juet_outpass.models

data class Post(
    val from:String="",
    val to:String="",
    val userID:String="",
    val purpose:String="",
    val placeOnLeave:String="",
    var status:String="",
    val createdAt: Long = 0L
)
