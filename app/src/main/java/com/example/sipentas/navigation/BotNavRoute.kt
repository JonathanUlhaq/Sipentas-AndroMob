package com.example.sipentas.navigation

import com.example.sipentas.R

sealed class BotNavRoute(val route:String,val icon:Int,val label:String) {
    object PenerimaManfaat:BotNavRoute("penerimaManfaat", R.drawable.penerima_manfaat_icon,"PM")
    object Assessment:BotNavRoute("assessment",R.drawable.assessment_icon,"Assessment")
    object Atensi:BotNavRoute("atensi",R.drawable.atensi_icon,"Atensi")
    object Profile:BotNavRoute("profile",R.drawable.profile_icon,"Profile")
}
