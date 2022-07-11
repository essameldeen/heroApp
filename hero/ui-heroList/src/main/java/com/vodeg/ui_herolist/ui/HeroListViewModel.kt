package com.vodeg.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.UIComponent
import com.vodeg.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel
@Inject constructor(
    private val getHeros: GetHeros,
) : ViewModel() {
     val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private val logger = Logger("HeroListViewModel")

    init {
        getHeros()
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
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(dataState.progressBarState)
                    // state.value = state.value.copy(progressBarState = dataState.progressBarState)

                }
            }
        }.launchIn(viewModelScope)
    }
}