package com.rutubishi.randomrecipes

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.rutubishi.randomrecipes.ui.RandomRecipesApp

@ExperimentalComposeUiApi
fun main(){
    CanvasBasedWindow(canvasElementId = "random-recipes-canvas") {
        RandomRecipesApp()
    }
}