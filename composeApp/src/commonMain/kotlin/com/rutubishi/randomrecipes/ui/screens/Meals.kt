package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rutubishi.randomrecipes.data.model.Meal
import com.rutubishi.randomrecipes.ui.isWideScreen
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import randomrecipes.composeapp.generated.resources.Res
import randomrecipes.composeapp.generated.resources.hello_welcome_to_random_recipes
import randomrecipes.composeapp.generated.resources.something_went_wrong

@Serializable
data class MealsRoute(
    val username: String,
    val selectedCategory: String = "Pasta",
)

@Composable
fun Meals(
    modifier: Modifier = Modifier,
    vm: MealsVM,
    route: MealsRoute,
    uiState: MealsScreenUiState,
    navController: NavHostController,
) {
    val state = uiState.appState
    LaunchedEffect(Unit) {
        vm.fetchMealsByCategory(route.selectedCategory)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 6.dp,
            contentPadding = PaddingValues(8.dp),
            columns = StaggeredGridCells.Fixed(if (isWideScreen()) 5 else 2),
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    stringResource(Res.string.hello_welcome_to_random_recipes, route.username),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                )
            }

            when (state) {
                is AppResource.Error -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(state.error.message ?: stringResource(Res.string.something_went_wrong))
                    }
                }
                is AppResource.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(75.dp),
                        )
                    }
                }
                is AppResource.Success -> {
                    items(state.data.meals, key = { it.idMeal }) { meal ->
                        MealItem(
                            meal = meal,
                            onClick = { _meal ->
                                navController.navigate(MealDetailRoute(_meal.idMeal))
                            },
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            onClick = {
                vm.refreshMeals()
            },
        ) {
            Icon(
                imageVector = Icons.TwoTone.Refresh,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun MealItem(
    modifier: Modifier = Modifier,
    meal: Meal,
    onClick: (Meal) -> Unit = {},
) {
    ElevatedCard(modifier = modifier, onClick = { onClick(meal) }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = null,
                modifier =
                    Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = meal.strMeal,
                textAlign = TextAlign.Start,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp),
            )
        }
    }
}
