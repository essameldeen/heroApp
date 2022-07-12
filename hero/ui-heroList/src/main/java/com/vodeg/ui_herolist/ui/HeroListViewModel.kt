package com.vodeg.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.UIComponent
import com.vodeg.hero_domain.Hero
import com.vodeg.hero_domain.HeroFilter
import com.vodeg.hero_interactors.FilterHeros
import com.vodeg.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel
@Inject constructor(
    private val getHeros: GetHeros,
    private @Named("heroListLogger") val logger: Logger,
    private val filterHeros: FilterHeros
) : ViewModel() {
    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())


    init {
        onEventTrigger(HeroListEvent.GetHeros)
    }

    fun onEventTrigger(event: HeroListEvent) {
        when (event) {
            is HeroListEvent.GetHeros -> {
                getHeros()
            }
            is HeroListEvent.FilterHero -> {
                filterHeros()
            }
            is HeroListEvent.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }
            is HeroListEvent.UpdateHeroFilter -> {
                updateHeroFilter(event.heroFilter)
            }
        }
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHeros()
    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
    }

    private fun filterHeros() {
        val filterList = filterHeros.execute(
            state.value.heroList,
            state.value.heroName,
            state.value.heroFilter,
            state.value.heroAttribute,
        )
        state.value = state.value.copy(filterHeros = filterList)
    }

    private fun getHeros() {
        getHeros.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log("dialog")
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                            logger.log((dataState.uiComponent as UIComponent.Dialog).title)
                        }
                        is UIComponent.None -> {
                            logger.log("non")
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroList = dataState.data ?: listOf())
                    filterHeros()
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(dataState.progressBarState)
                    // state.value = state.value.copy(progressBarState = dataState.progressBarState)

                }
            }
        }.launchIn(viewModelScope)
    }
}