package com.example.sipentas.repositories

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.AddPmResponse
import com.example.sipentas.models.DetailPmResponse
import com.example.sipentas.models.KecamatanModel
import com.example.sipentas.models.KelurahanModel
import com.example.sipentas.models.PmUpdateBody
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.models.verifikasi_assesment.VerifikasiAssesmentResponse
import com.example.sipentas.network.SipentasAPI
import okhttp3.MultipartBody
import javax.inject.Inject

class PmFormRepository @Inject constructor(val api:SipentasAPI)  {
    suspend fun getProvinsi():List<ProvinsiModel> = api.getProvinsi()
    suspend fun getKabupaten(id:Int):List<KabupatenModel> = api.getKabupaten(id)
    suspend fun getRagam(id:Int):List<RagamModel> = api.getRagam(id)
    suspend fun getKategori():List<KategoriModel?> = api.getKategori()
    suspend fun getKecamatan(id:Int):List<KecamatanModel> = api.getKecamatan(id)
    suspend fun getKelurahan(id:Int):List<KelurahanModel> = api.getKelurahan(id)
    suspend fun getPmDetail(id:Int):DetailPmResponse = api.getDetailPm(id)

    suspend fun insertPhoto(foto:MultipartBody.Part):UploadResponse = api.uploadPhoto(foto)
    suspend fun addPm(body:PostPmModel):AddPmResponse = api.addPm(body)
    suspend fun updatePm(id:Int,body:PmUpdateBody) = api.updatePm(id,body)

    suspend fun getAssesmentPm(id:Int):VerifikasiAssesmentResponse = api.getAssesmentPm(id)
}