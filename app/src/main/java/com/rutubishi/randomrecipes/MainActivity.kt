package com.rutubishi.randomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rutubishi.randomrecipes.data.repository.MealRepository
import com.rutubishi.randomrecipes.ui.screens.Login
import com.rutubishi.randomrecipes.ui.screens.LoginRoute
import com.rutubishi.randomrecipes.ui.screens.LoginVM
import com.rutubishi.randomrecipes.ui.screens.MealDetail
import com.rutubishi.randomrecipes.ui.screens.MealDetailRoute
import com.rutubishi.randomrecipes.ui.screens.MealDetailVM
import com.rutubishi.randomrecipes.ui.screens.Meals
import com.rutubishi.randomrecipes.ui.screens.MealsRoute
import com.rutubishi.randomrecipes.ui.screens.MealsVM
import com.rutubishi.randomrecipes.ui.theme.RandomRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomRecipesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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
                        enterTransition = { slideInHorizontally() },
                        exitTransition = { slideOutHorizontally() },
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
                            )
                        }
                    }
                }
            }
        }
    }
}
