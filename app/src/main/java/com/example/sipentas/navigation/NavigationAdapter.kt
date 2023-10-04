package com.example.sipentas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sipentas.view.assessment.AssessmentView
import com.example.sipentas.view.atensi.Atensi
import com.example.sipentas.view.form.FormView
import com.example.sipentas.view.list_pm.ListPmView
import com.example.sipentas.view.list_pm.ListPmViewModel
import com.example.sipentas.view.login.LoginView
import com.example.sipentas.view.login.LoginViewModel

@Composable
fun NavigationAdapter(navController: NavHostController,showBottomBar:MutableState<Boolean>) {

    val loginViewModel = hiltViewModel<LoginViewModel>()
    val listViewModel = hiltViewModel<ListPmViewModel>()

    NavHost(navController = navController, startDestination = AppRoute.Login.route ) {
        composable(AppRoute.Login.route) {
            showBottomBar.value = false
            LoginView(navController,loginViewModel)
        }
        composable(AppRoute.Form.route) {
            showBottomBar.value = false
            FormView(navController)
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
            Atensi()
        }
        composable(BotNavRoute.Profile.route) {
            showBottomBar.value = true
            Atensi()
        }
    }
}