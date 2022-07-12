package com.vodeg.core

sealed class UIComponentState {
    object Show : UIComponentState()
    object Hide : UIComponentState()

}
