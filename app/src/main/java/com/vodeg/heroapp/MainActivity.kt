package com.vodeg.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.vodeg.heroapp.navigation.Screen
import com.vodeg.ui_herodetail.ui.HeroDetails
import com.vodeg.ui_herodetail.ui.HeroDetailsViewModel
import com.vodeg.ui_herolist.HeroList
import com.vodeg.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController, startDestination = Screen.HeroList.route,
                builder = {
                    addHeroListScreen(navController = navController, imageLoader = imageLoader)
                    addDetailsScreen(imageLoader = imageLoader)
                }
            )

        }
    }
}

fun NavGraphBuilder.addHeroListScreen(
    navController: NavController,
    imageLoader: ImageLoader
) {
    composable(route = Screen.HeroList.route) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(
            state = viewModel.state.value,
            imageLoader = imageLoader, { heroId ->
                navController.navigate("${Screen.HeroDetails.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addDetailsScreen(
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroDetails.route + "/{heroId}",
        arguments = Screen.HeroDetails.arguments
    ) {
        val heroDetailsViewModel: HeroDetailsViewModel = hiltViewModel()
        HeroDetails(
            state = heroDetailsViewModel.state.value,
            imageLoader = imageLoader
        )
    }
}