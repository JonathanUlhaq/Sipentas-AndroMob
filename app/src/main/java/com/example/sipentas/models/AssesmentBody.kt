package com.example.sipentas.models

data class AssesmentBody(
    val id_pendidikan:Int?,
    val id_sumber_kasus:Int?,
    val id_pekerjaan:Int?,
    val id_pm:Int?,
    val id_lembaga:Int?,
    val tanggal:String?,
    val petugas:String?,
    val status_dtks:String?,
    val id_kerja_ortu:Int?,
    val id_status_ortu:Int?,
    val nama_bpk:String?,
    val nama_ibu:String?,
    val nik_ibu:String?,
    val nama_wali:String?,
    val flag:Int?,
    val id_tempat_tgl:Int?,
    val catatan:String?,
    val foto_rumah:String?,
    val foto_kondisi_fisik:String?,
    val foto_kk:String?,
    val foto_ktp:String?,
    val long:String?,
    val lat:String?

)
