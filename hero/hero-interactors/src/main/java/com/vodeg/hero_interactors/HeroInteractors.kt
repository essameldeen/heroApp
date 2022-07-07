package com.vodeg.hero_interactors

import com.vodeg.hero_datasource.network.HeroService

data class HeroInteractors(
     val getHeros: GetHeros,
    //TODO other useCase
) {

    companion object Factory {
        fun build(): HeroInteractors {
            val service = HeroService.build()
            return HeroInteractors(
                getHeros = GetHeros(
                    heroService = service
                )
            )
        }
    }
}
