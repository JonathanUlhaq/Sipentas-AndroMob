package com.example.sipentas.models

data class PmUpdateBody(
    val name:String,
    val nik:String,
    val place_of_birth:String,
    val date_of_birth:String,
    val gender:String,
    val religion:Int,
    val kode_pos:String,
    val phone_number:String,
    val provinsi_id:Int,
    val kabupaten_id:Int,
    val nama_jalan:String,
    val flag:Int,
    val kecamatan_id:Int,
    val kelurahan_id:Long,
    val satker_id:Int,
    val kluster_id:Int,
    val ragam_id:Int,
    val ket_ppks:String,
    val foto_diri:String
)
