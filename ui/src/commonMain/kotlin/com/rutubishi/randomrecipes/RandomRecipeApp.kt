package com.rutubishi.randomrecipes

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rutubishi.randomrecipes.data.repository.MealRepository
import com.rutubishi.randomrecipes.presentation.login.Login
import com.rutubishi.randomrecipes.presentation.login.LoginRoute
import com.rutubishi.randomrecipes.presentation.login.LoginVM
import com.rutubishi.randomrecipes.presentation.mealdetail.MealDetail
import com.rutubishi.randomrecipes.presentation.mealdetail.MealDetailRoute
import com.rutubishi.randomrecipes.presentation.mealdetail.MealDetailVM
import com.rutubishi.randomrecipes.presentation.meals.Meals
import com.rutubishi.randomrecipes.presentation.meals.MealsRoute
import com.rutubishi.randomrecipes.presentation.meals.MealsVM
import com.rutubishi.randomrecipes.theme.RandomRecipesTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    RandomRecipesTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
        ) { innerPadding ->

            val navController = rememberNavController()
            val mealRepo = MealRepository()

            val loginVM = LoginVM(mealRepo)
            val mealsVM = MealsVM(mealRepo)
            val mealDetailVM = MealDetailVM(mealRepo)

            val mealsScreenUiState by mealsVM.uiState.collectAsState()
            val mealDetailScreenUiState by mealDetailVM.uiState.collectAsState()

            NavHost(
                navController = navController,
                startDestination = LoginRoute,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                composable<LoginRoute> {
                    Login(
                        modifier = Modifier.padding(innerPadding),
                        navHostController = navController,
                        vm = loginVM,
                    )
                }

                composable<MealsRoute> {
                    val route = it.toRoute<MealsRoute>()
                    Meals(
                        modifier = Modifier.padding(innerPadding),
                        route = route,
                        vm = mealsVM,
                        uiState = mealsScreenUiState,
                        navController = navController,
                    )
                }

                composable<MealDetailRoute> {
                    val route = it.toRoute<MealDetailRoute>()
                    MealDetail(
                        modifier = Modifier.padding(innerPadding),
                        route = route,
                        uiState = mealDetailScreenUiState,
                        vm = mealDetailVM,
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
expect fun isAppInWideScreen(): Boolean
