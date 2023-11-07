package com.example.sipentas.models

data class AtensiBody(
    val id_jenis:Int,
    val id_pm:Int,
    val id_assesment:Int,
    val jenis:String?,
    val nilai:Long?,
    val tanggal:String,
    val id_pendekatan:Int,
    val penerima:String,
    val foto:String?,
    val long:String,
    val lat:String
)
