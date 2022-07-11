package com.vodeg.heroapp.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object HeroList : Screen(
        route = "heroList",
        arguments = emptyList()
    )
    object HeroDetails : Screen(
        route = "heroDetails",
        arguments = listOf(
            navArgument("heroId"){
                type = NavType.IntType
            }
        )
    )
}
