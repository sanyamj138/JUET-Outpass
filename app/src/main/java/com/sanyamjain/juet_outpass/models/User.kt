package com.sanyamjain.juet_outpass.models

data class User(
    val uid: String = "",
    val displayName: String? = "",
    val email: String = "",
    val branch: String ="",
    val year: Int = 0,
    val sem : Int = 0,
    val studentNumber : String="",
    val parentNumber: String="",
    val parentEmail:String="",
    val homeAddress : String = "",
    val hostel : String = "",
    val roomNo : String = "",
    val bedNo : Int= 0
)
