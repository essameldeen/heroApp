package com.vodeg.hero_domain

import com.vodeg.core.FilterOrder

sealed class HeroFilter(
    val uiValue: String
) {

    data class Hero(
        val order: FilterOrder = FilterOrder.Descending
    ) : HeroFilter("Hero")

    data class ProWin(
        val order: FilterOrder = FilterOrder.Descending
    ) : HeroFilter("Pro win-rate")
}
