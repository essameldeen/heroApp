package com.vodeg.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.vodeg.core.ProgressBarState
import com.vodeg.ui_herolist.HeroListItem
import com.vodeg.ui_herolist.components.HeroListFilter
import com.vodeg.ui_herolist.components.HeroListToolbar

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvent) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailsScreen: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {

            HeroListToolbar(heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvent.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvent.FilterHero)
                },
                onShowFilterDialog = {

                })


            LazyColumn {
                items(state.filterHeros) { hero ->
                    HeroListItem(hero, imageLoader, { heroId ->
                        navigateToDetailsScreen(heroId)
                    })

                }
            }
        }
        HeroListFilter(heroFilter =state.heroFilter,
            onUpdateHeroFilter ={ heroFilter->
                events(HeroListEvent.UpdateHeroFilter(heroFilter))
            }, onCloseDialog ={

            })


        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}