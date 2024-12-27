package com.rutubishi.randomrecipes.util

import androidx.compose.ui.unit.dp

object Dimensions {
    fun XXSmall(isWide: Boolean) = if (isWide) 4.dp else 2.dp

    fun XSmall(isWide: Boolean) = if (isWide) 8.dp else 4.dp

    fun Small(isWide: Boolean) = if (isWide) 16.dp else 8.dp

    fun Medium(isWide: Boolean) = if (isWide) 32.dp else 16.dp

    fun Large(isWide: Boolean) = if (isWide) 64.dp else 32.dp

    fun XLarge(isWide: Boolean) = if (isWide) 128.dp else 64.dp

    fun XXLarge(isWide: Boolean) = if (isWide) 256.dp else 128.dp

    fun XXXLarge(isWide: Boolean) = if (isWide) 512.dp else 256.dp
}
