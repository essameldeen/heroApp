package com.vodeg.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.vodeg.core.ProgressBarState

@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(state.heroList) { hero ->
                HeroListItem(hero, imageLoader, { heroId ->

                })

            }
        }
        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}