package com.example.sipentas.navigation

sealed class AppRoute(val route:String) {
    object Login:AppRoute("login")
    object Form:AppRoute("route")
    object ListPM:AppRoute("listpm")
    object DetailPm:AppRoute("detailpm")
    object FormAssessment:AppRoute("formassessment")
    object DetailAtensi:AppRoute("detailatensi")
    object UpdatePassword:AppRoute("updatepassword")
    object DetailAssessment:AppRoute("detailassessment")
}
