package com.vodeg.ui_herodetail.ui

sealed class HeroDetailsEvent {
    data class GetHeroCache(
        val id: Int
    ) : HeroDetailsEvent()

    object RemoveHeadFromQueue : HeroDetailsEvent()

}
