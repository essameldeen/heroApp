package com.vodeg.ui_herodetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vodeg.ui_herodetail.ui.HeroDetailsState

@Composable
fun HeroDetails(
    state: HeroDetailsState
) {
    state.hero?.let { hero ->

        Text(hero.localizedName)
    } ?: Text("Loading...")
}