package com.example.sipentas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sipentas.view.all_in_form.AllInFormView
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.view.assessment.AssessmentView
import com.example.sipentas.view.atensi.Atensi
import com.example.sipentas.view.change_password.ChangePasswordView
import com.example.sipentas.view.detail_assessment.DetailAssessmentView
import com.example.sipentas.view.detail_atensi.DetailAtens
import com.example.sipentas.view.detail_atensi.DetailAtensiViewModel
import com.example.sipentas.view.detail_view.DetailView
import com.example.sipentas.view.form_assessment.FormAssessment
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.view.form_pm.FormView
import com.example.sipentas.view.list_pm.ListPmView
import com.example.sipentas.view.list_pm.ListPmViewModel
import com.example.sipentas.view.login.LoginView
import com.example.sipentas.view.login.LoginViewModel
import com.example.sipentas.view.profile.ProfileView
import com.example.sipentas.view.profile.ProfileViewModel

@Composable
fun NavigationAdapter(navController: NavHostController, showBottomBar: MutableState<Boolean>) {

    val loginViewModel = hiltViewModel<LoginViewModel>()
    val listViewModel = hiltViewModel<ListPmViewModel>()
    val formPmViewModel = hiltViewModel<FormPmViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val assesmenViewModel = hiltViewModel<AssesmenViewModel>()
    val detailAtensiVm = hiltViewModel<DetailAtensiViewModel>()

    NavHost(
        navController = navController,
        startDestination = if (loginViewModel.getToken()
                .isNullOrEmpty()
        ) AppRoute.Login.route else BotNavRoute.PenerimaManfaat.route
    ) {
        composable(AppRoute.Login.route) {
            showBottomBar.value = false
            LoginView(navController, loginViewModel)
        }
        composable(AppRoute.Form.route) {
            showBottomBar.value = false
//            FormView(navController, formPmViewModel)
            AllInFormView(navController = navController, formPmViewModel, assesmenViewModel,detailAtensiVm)
        }

        composable(BotNavRoute.PenerimaManfaat.route) {
            showBottomBar.value = true
            ListPmView(listViewModel, navController = navController)
        }
        composable(BotNavRoute.Assessment.route) {
            showBottomBar.value = true
            AssessmentView(navController = navController, assesmenViewModel)
        }

        composable(BotNavRoute.Atensi.route) {
            showBottomBar.value = true
            Atensi(navController)
        }
        composable(BotNavRoute.Profile.route) {
            showBottomBar.value = true
            ProfileView(navController, profileViewModel)
        }

        composable(
            AppRoute.DetailPm.route
                    + "/{ragam}/" +
                    "{kelurahanId}/" +
                    "{id}/" +
                    "{agamaId}/" +
                    "{ragamId}/" +
                    "{nama}/" +
                    "{kelamin}/" +
                    "{agama}/" +
                    "{provinsi}/" +
                    "{kabupaten}/" +
                    "{kluster}/" +
                    "{id_kluster}/" +
                    "{id_provinsi}/" +
                    "{ket_ppks}/" +
                    "{tempat_lahir}/" +
                    "{tanggal_lahir}/" +
                    "{phone}/" +
                    "{nik}/" +
                    "{kelurahan}/" +
                    "{kec_id}/" +
                    "{kecamatan}/" +
                    "{kab_id}/" +
                    "{jalan}?" +
                    "foto_diri={foto_diri}",
            arguments = listOf(
                navArgument("nama") {
                    type = NavType.StringType
                },
                navArgument("ket_ppks") {
                    defaultValue = "empty"
                },
                navArgument("tempat_lahir") {
                    defaultValue = "empty"
                },
                navArgument("tanggal_lahir") {
                    defaultValue = "empty"
                },
                navArgument("phone") {
                    defaultValue = ""
                },
                navArgument("nik") {
                    defaultValue = ""
                },
                navArgument("kelurahan") {
                    defaultValue = ""
                },
                navArgument("kec_id") {
                    defaultValue = "0"
                },
                navArgument("kecamatan") {
                    defaultValue = ""
                },
                navArgument("kab_id") {
                    defaultValue = "0"
                },
                navArgument("jalan") {
                    defaultValue = ""
                }
            )
        ) {
            showBottomBar.value = false
            DetailView(
                navController = navController,
                vm = formPmViewModel,
                currentRagam = it.arguments?.getString("ragam")!!,
                currentName = it.arguments?.getString("nama")!!,
                currentKelamin = it.arguments?.getString("kelamin")!!,
                currentAgama = it.arguments?.getString("agama")!!,
                currentProvinsi = it.arguments?.getString("provinsi")!!,
                currentKabupaten = it.arguments?.getString("kabupaten")!!,
                currentKluster = it.arguments?.getString("kluster")!!,
                currentIdKluster = it.arguments?.getString("id_kluster")!!,
                currentIdProvinsi = it.arguments?.getString("id_provinsi")!!,
                ketPpks = it.arguments?.getString("ket_ppks")!!,
                currentTempatLahir = it.arguments?.getString("tempat_lahir")!!,
                currentTanggalLahir = it.arguments?.getString("tanggal_lahir")!!,
                currentNomorHandphone = it.arguments?.getString("phone")!!,
                currentNik = it.arguments?.getString("nik")!!,
                currentKelurahan = it.arguments?.getString("kelurahan")!!,
                currentKecId = it.arguments?.getString("kec_id")!!,
                currentKecamatan = it.arguments?.getString("kecamatan")!!,
                currentKabupId = it.arguments?.getString("kab_id")!!,
                currentJalan = it.arguments?.getString("jalan")!!,
                fotoDiri = it.arguments?.getString("foto_diri")!!,
                asVm = assesmenViewModel,
                id_pm = it.arguments?.getString("id")!!,
                currentAgamaId = it.arguments?.getString("agamaId")!!,
                currentRagamId = it.arguments?.getString("ragamId")!!,
                currentKelurahanId = it.arguments?.getString("kelurahanId")!!
            )
        }

        composable(AppRoute.FormAssessment.route + "/{id}") {
            showBottomBar.value = false
            FormAssessment(
                navController = navController,
                asVm = assesmenViewModel,
                vm = formPmViewModel,
                idUser = it.arguments?.getString("id")!!
            )
        }

        composable(AppRoute.DetailAtensi.route) {
            showBottomBar.value = false
            DetailAtens(navController = navController,detailAtensiVm)
        }

        composable(AppRoute.UpdatePassword.route) {
            showBottomBar.value = false
            ChangePasswordView(navController = navController)
        }

        composable(
            AppRoute.DetailAssessment.route
                    + "/{pendidikan}"
                    + "/{pendidikanId}"
                    + "/{sumber}"
                    + "/{sumberId}"
                    + "/{pekerjaan}"
                    + "/{pekerjaanId}"
                    + "/{tanggal}"
                    + "/{petugas}"
                    + "/{dtks}"
                    + "/{status}"
                    + "/{statusId}"
                    + "/{pekerjaanOrtu}"
                    + "/{pekerjaanOrtuId}"
                    + "/{tempatTinggal}"
                    + "/{tempatTinggalId}"
                    + "/{namaBapak}"
                    + "/{namaIbu}"
                    + "/{nikIbu}"
                    + "/{namaWali}"
                    + "/{penghasilan}"
                    + "/{catatan}?"
                    + "urlRumah={urlRumah}?"
                    + "urlFisik={urlFisik}?"
                    + "urlKk={urlKk}?"
                    + "urlKtp={urlKtp}"
        ) {
            showBottomBar.value = false
            DetailAssessmentView(
                navController = navController,
                assesmenViewModel,
                formPmViewModel,
                urlRumah = it.arguments?.getString("urlRumah")!!,
                urlFisik = it.arguments?.getString("urlFisik")!!,
                urlKk = it.arguments?.getString("urlKk")!!,
                urlKtp = it.arguments?.getString("urlKtp")!!,
                curPendidikan = it.arguments?.getString("pendidikan")!!,
                curPendidikanId = it.arguments?.getString("pendidikanId")!!,
                curSumber = it.arguments?.getString("sumber")!!,
                curSumberId = it.arguments?.getString("sumberId")!!,
                curPekerjaan = it.arguments?.getString("pekerjaan")!!,
                curPekerjaanId = it.arguments?.getString("pekerjaanId")!!,
                curTanggal = it.arguments?.getString("tanggal")!!,
                curPetugas = it.arguments?.getString("petugas")!!,
                curDtks = it.arguments?.getString("dtks")!!,
                curStatusOrtu = it.arguments?.getString("status")!!,
                curStatusOrtuId = it.arguments?.getString("statusId")!!,
                curPekerOrtu = it.arguments?.getString("pekerjaanOrtu")!!,
                curPekerOrtuId =it.arguments?.getString("pekerjaanOrtuId")!! ,
                curTempatTinggal = it.arguments?.getString("tempatTinggal")!!,
                curTempatTinggalId = it.arguments?.getString("tempatTinggalId")!!,
                curNamaBapak = it.arguments?.getString("namaBapak")!!,
                curNamaIbu = it.arguments?.getString("namaIbu")!!,
                curNikIbu = it.arguments?.getString("nikIbu")!!,
                curNamaWali = it.arguments?.getString("namaWali")!!,
                curPenghasilan = it.arguments?.getString("penghasilan")!!,
                curCatatan = it.arguments?.getString("catatan")!!
            )
        }

    }
}