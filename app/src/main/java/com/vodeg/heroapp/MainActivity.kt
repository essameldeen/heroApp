package com.vodeg.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.squareup.sqldelight.android.AndroidSqliteDriver

import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.ProgressBarState
import com.vodeg.core.UIComponent
import com.vodeg.hero_domain.Hero
import com.vodeg.hero_interactors.HeroInteractors
import com.vodeg.heroapp.ui.theme.HeroAppTheme
import com.vodeg.ui_herolist.HeroList
import com.vodeg.ui_herolist.HeroListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {
    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private val progressBarState: MutableState<ProgressBarState> =
        mutableStateOf(ProgressBarState.Idle)
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .build()
        val getHeros = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName,
            )
        ).getHeros
        val logger = Logger("GetHerosTest")
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
                    progressBarState.value = dataState.progressBarState
                    // state.value = state.value.copy(progressBarState = dataState.progressBarState)

                }
            }
        }.launchIn(CoroutineScope(IO))

        setContent {
            HeroAppTheme {
                HeroList(
                    state = state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}