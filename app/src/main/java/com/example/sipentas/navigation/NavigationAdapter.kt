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
import com.example.sipentas.view.assessment.AssessmentView
import com.example.sipentas.view.atensi.Atensi
import com.example.sipentas.view.detail_atensi.DetailAtens
import com.example.sipentas.view.detail_view.DetailView
import com.example.sipentas.view.form_assessment.FormAssessment
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.view.form_pm.FormView
import com.example.sipentas.view.list_pm.ListPmView
import com.example.sipentas.view.list_pm.ListPmViewModel
import com.example.sipentas.view.login.LoginView
import com.example.sipentas.view.login.LoginViewModel

@Composable
fun NavigationAdapter(navController: NavHostController, showBottomBar: MutableState<Boolean>) {

    val loginViewModel = hiltViewModel<LoginViewModel>()
    val listViewModel = hiltViewModel<ListPmViewModel>()
    val formPmViewModel = hiltViewModel<FormPmViewModel>()


    NavHost(navController = navController, startDestination = if (loginViewModel.getToken() == null)AppRoute.Login.route else BotNavRoute.PenerimaManfaat.route) {
        composable(AppRoute.Login.route) {
            showBottomBar.value = false
            LoginView(navController, loginViewModel)
        }
        composable(AppRoute.Form.route) {
            showBottomBar.value = false
            FormView(navController, formPmViewModel)
        }

        composable(BotNavRoute.PenerimaManfaat.route) {
            showBottomBar.value = true
            ListPmView(listViewModel, navController = navController)
        }
        composable(BotNavRoute.Assessment.route) {
            showBottomBar.value = true
            AssessmentView(navController = navController)
        }
        composable(BotNavRoute.Atensi.route) {
            showBottomBar.value = true
            Atensi(navController)
        }
        composable(BotNavRoute.Profile.route) {
            showBottomBar.value = true
            Atensi(navController)
        }

        composable(
            AppRoute.DetailPm.route + "/{ragam}/{nama}/{kelamin}/{agama}/{provinsi}/{kabupaten}",
            arguments = listOf(
                navArgument("nama") {
                    type = NavType.StringType
                }
            )
        ) {
            showBottomBar.value = false
            DetailView(navController = navController,
                vm = formPmViewModel,
                currentRagam =it.arguments?.getString("ragam")!! ,
                currentName = it.arguments?.getString("nama")!!,
                currentKelamin = it.arguments?.getString("kelamin")!!,
                currentAgama = it.arguments?.getString("agama")!!,
                currentProvinsi = it.arguments?.getString("provinsi")!!,
                currentKabupaten = it.arguments?.getString("kabupaten")!!)
        }

        composable(AppRoute.FormAssessment.route) {
            showBottomBar.value = false
            FormAssessment(navController = navController)
        }

        composable(AppRoute.DetailAtensi.route) {
            showBottomBar.value = false
            DetailAtens(navController = navController)
        }

    }
}