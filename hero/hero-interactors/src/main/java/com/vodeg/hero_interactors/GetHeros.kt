package com.vodeg.hero_interactors

import com.vodeg.core.DataState
import com.vodeg.core.ProgressBarState
import com.vodeg.core.UIComponent
import com.vodeg.hero_datasource.cache.HeroCache
import com.vodeg.hero_datasource.network.HeroService
import com.vodeg.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetHeros(
    private val cache: HeroCache,
    private val service: HeroService,
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heros: List<Hero> = try { // catch network exceptions
                service.getHeroStats()
            }catch (e: Exception){
                e.printStackTrace() // log to crashlytics?
                emit(DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message?: "Unknown error"
                    )
                ))
                listOf()
            }

            // cache the network data
            cache.insert(heros)

            // emit data from cache
            val cachedHeros = cache.selectAll()

            emit(DataState.Data(cachedHeros))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Response<List<Hero>>(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message?: "Unknown error"
                )
            ))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}
