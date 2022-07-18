package com.vodeg.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vodeg.core.ProgressBarState
import com.vodeg.core.Queue
import com.vodeg.core.UIComponent

@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent> = Queue(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content()
            if (!queue.isEmpty()) {
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog) {
                        GenericDialog(
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)

                        )
                    }

                }
            }
            if (progressBarState is ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }
        }
    }
}