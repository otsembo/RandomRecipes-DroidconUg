package com.rutubishi.randomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rutubishi.randomrecipes.data.repository.MealRepository
import com.rutubishi.randomrecipes.ui.screens.Login
import com.rutubishi.randomrecipes.ui.screens.LoginVM
import com.rutubishi.randomrecipes.ui.theme.RandomRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomRecipesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(modifier = Modifier.padding(innerPadding), vm = LoginVM(mealRepository = MealRepository()))
                }
            }
        }
    }
}
