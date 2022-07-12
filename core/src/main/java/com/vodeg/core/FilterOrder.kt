package com.vodeg.core

sealed class FilterOrder {
    object Ascending : FilterOrder()
    object Descending : FilterOrder()
}
