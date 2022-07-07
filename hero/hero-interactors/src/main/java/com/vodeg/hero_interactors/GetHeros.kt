package com.vodeg.hero_interactors

import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.ProgressBarState
import com.vodeg.core.UIComponent
import com.vodeg.hero_datasource.network.HeroService
import com.vodeg.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val heroService: HeroService,

    ) {

     fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(ProgressBarState.Loading))
            val heros: List<Hero> = try {
                heroService.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Error",
                            description = e.message ?: "UnKnown Error"
                        )
                    )
                )
                listOf()
            }
            //TODO (caching)
            emit(DataState.Data(heros))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "UnKnown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }
}