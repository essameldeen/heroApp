package com.vodeg.ui_herodetail.ui

import com.vodeg.core.ProgressBarState
import com.vodeg.hero_domain.Hero

data class HeroDetailsState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null
)
