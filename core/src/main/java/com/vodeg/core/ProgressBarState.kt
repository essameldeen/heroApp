package com.vodeg.core

sealed class ProgressBarState {
    object Loading : ProgressBarState()
    object Idle : ProgressBarState()
}