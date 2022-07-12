package com.vodeg.ui_herolist.ui

import com.vodeg.hero_domain.HeroFilter

sealed class HeroListEvent {

    object GetHeros : HeroListEvent()
    object FilterHero : HeroListEvent()
    data class UpdateHeroName(
        val heroName: String
    ) : HeroListEvent()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ):HeroListEvent()
}
