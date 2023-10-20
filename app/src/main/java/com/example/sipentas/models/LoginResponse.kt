package com.example.sipentas.models

data class LoginResponse(
//    val user:User? = null,
    val token:String? = null,
    val message:String? = null,
    val user:UserResponse? = null
)
