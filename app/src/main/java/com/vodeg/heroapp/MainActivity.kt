package com.vodeg.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.squareup.sqldelight.android.AndroidSqliteDriver

import com.vodeg.core.DataState
import com.vodeg.core.Logger
import com.vodeg.core.ProgressBarState
import com.vodeg.core.UIComponent
import com.vodeg.hero_interactors.HeroInteractors
import com.vodeg.heroapp.ui.theme.HeroAppTheme
import com.vodeg.ui_herolist.HeroList
import com.vodeg.ui_herolist.ui.HeroListState
import com.vodeg.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeroAppTheme {
                val viewModel: HeroListViewModel = hiltViewModel()
                HeroList(
                    state = viewModel.state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}