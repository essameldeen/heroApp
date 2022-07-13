package com.vodeg.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vodeg.heroapp.navigation.Screen
import com.vodeg.ui_herodetail.ui.HeroDetails
import com.vodeg.ui_herodetail.ui.HeroDetailsViewModel
import com.vodeg.ui_herolist.ui.HeroList
import com.vodeg.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberAnimatedNavController()
            BoxWithConstraints {
                AnimatedNavHost(
                    navController = navController, startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroListScreen(
                            navController = navController,
                            imageLoader = imageLoader,
                            width = constraints.maxWidth / 2,
                        )
                        addDetailsScreen(
                            imageLoader = imageLoader,
                            width = constraints.maxWidth / 2,
                        )
                    }
                )

            }

        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addHeroListScreen(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int
) {
    composable(route = Screen.HeroList.route,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(
            state = viewModel.state.value,
            events = viewModel::onEventTrigger,
            imageLoader = imageLoader,
            { heroId ->
                navController.navigate("${Screen.HeroDetails.route}/$heroId")
            }
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addDetailsScreen(
    imageLoader: ImageLoader,
    width: Int
) {
    composable(
        route = Screen.HeroDetails.route + "/{heroId}",
        arguments = Screen.HeroDetails.arguments,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val heroDetailsViewModel: HeroDetailsViewModel = hiltViewModel()
        HeroDetails(
            state = heroDetailsViewModel.state.value,
            imageLoader = imageLoader
        )
    }
}