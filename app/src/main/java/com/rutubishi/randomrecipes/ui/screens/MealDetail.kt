package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rutubishi.randomrecipes.R
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.serialization.Serializable

@Serializable
data class MealDetailRoute(
    val mealId: String,
)

@Composable
fun MealDetail(
    modifier: Modifier = Modifier,
    route: MealDetailRoute,
    uiState: MealDetailScreenUiState,
    vm: MealDetailVM,
) {
    val state = uiState.appState
    LaunchedEffect(Unit) {
        vm.fetchMealById(route.mealId)
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state) {
            is AppResource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(state.error.message ?: stringResource(R.string.something_went_wrong))
                }
            }
            is AppResource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .size(100.dp)
                                .testTag("progress_indicator"),
                    )
                }
            }
            is AppResource.Success -> {
                val mealItem = state.data.meals.last()

                AsyncImage(
                    model = mealItem.strMealThumb,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = mealItem.strMeal ?: stringResource(R.string.meal_here),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Text(
                    text = mealItem.strInstructions ?: stringResource(R.string.some_instructions),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}
