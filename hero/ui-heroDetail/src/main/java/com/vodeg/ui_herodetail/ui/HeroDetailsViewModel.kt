package com.vodeg.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.core.DataState
import com.vodeg.core.ProgressBarState
import com.vodeg.hero_interactors.GetHeroFromCache
import com.vodeg.ui_herodetail.HeroDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailsViewModel
@Inject constructor(
    private val getHeroCache: GetHeroFromCache,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val state: MutableState<HeroDetailsState> = mutableStateOf(HeroDetailsState())

    init {
        val id = savedStateHandle.get<Int>("heroId")?.let { heroId->
            onTriggerEvent(HeroDetailsEvent.GetHeroCache(heroId))
        }

    }


    fun onTriggerEvent(event: HeroDetailsEvent) {
        when (event) {
            is HeroDetailsEvent.GetHeroCache -> {
                getHeroFromCache(event.id)
            }
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroCache.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {

                }
            }
        }.launchIn(viewModelScope)
    }

}