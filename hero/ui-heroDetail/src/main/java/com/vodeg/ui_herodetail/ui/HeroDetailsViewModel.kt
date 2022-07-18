package com.vodeg.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.Queue
import com.vodeg.core.UIComponent
import com.vodeg.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroDetailsViewModel
@Inject constructor(
    private val getHeroCache: GetHeroFromCache,
    private val savedStateHandle: SavedStateHandle,
    private @Named("heroListLogger") val logger: Logger,

    ) : ViewModel() {
    val state: MutableState<HeroDetailsState> = mutableStateOf(HeroDetailsState())

    init {
        val id = savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailsEvent.GetHeroCache(heroId))
        }

    }


    fun onTriggerEvent(event: HeroDetailsEvent) {
        when (event) {
            is HeroDetailsEvent.GetHeroCache -> {
                getHeroFromCache(event.id)
            }
            is HeroDetailsEvent.RemoveHeadFromQueue -> {
                removeHeadMessageFromQueue()
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
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            addErrorMessageToQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {

                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addErrorMessageToQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf()))
        state.value = state.value.copy(errorQueue = queue)

    }

    private fun removeHeadMessageFromQueue() {

        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue(mutableListOf()))
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("No Message exist for removing ")
        }
    }
}