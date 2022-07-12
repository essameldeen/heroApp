package com.vodeg.ui_herolist.ui

sealed class HeroListEvent {

    object GetHeros : HeroListEvent()
    object FilterHero : HeroListEvent()
    data class UpdateHeroName(
        val heroName: String
    ) : HeroListEvent()
}
