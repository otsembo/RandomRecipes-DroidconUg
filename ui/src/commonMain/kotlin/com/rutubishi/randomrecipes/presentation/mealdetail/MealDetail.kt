package com.rutubishi.randomrecipes.presentation.mealdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rutubishi.randomrecipes.isAppInWideScreen
import com.rutubishi.randomrecipes.util.AppResource
import com.rutubishi.randomrecipes.util.Dimensions
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import randomrecipes.ui.generated.resources.Res
import randomrecipes.ui.generated.resources.meal_here
import randomrecipes.ui.generated.resources.some_instructions
import randomrecipes.ui.generated.resources.something_went_wrong

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
    navController: NavHostController,
) {
    val state = uiState.appState
    LaunchedEffect(Unit) {
        vm.fetchMealById(route.mealId)
    }
    val isWide = isAppInWideScreen()

    Column(
        modifier = modifier.padding(Dimensions.Medium(isWide)),
    ) {
        Icon(
            Icons.AutoMirrored.TwoTone.ArrowBack,
            contentDescription = null,
            modifier =
                Modifier
                    .padding(bottom = Dimensions.Medium(isWide))
                    .size(
                        Dimensions.Medium(isWide),
                    ).clickable { navController.popBackStack() },
        )

        when (state) {
            is AppResource.Error ->
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.error.message ?: stringResource(Res.string.something_went_wrong))
                }
            is AppResource.Loading ->
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .size(100.dp)
                                .testTag("progress_indicator"),
                    )
                }
            is AppResource.Success -> {
                val mealItem = state.data.meals.last()
                if (isWide) {
                    Row(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        AsyncImage(
                            model = mealItem.strMealThumb,
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .weight(3f),
                            contentScale = ContentScale.Crop,
                        )

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(start = Dimensions.Medium(isWide), end = Dimensions.Medium(isWide))
                                    .weight(5f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = mealItem.strMeal ?: stringResource(Res.string.meal_here),
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(top = Dimensions.Medium(isWide)),
                            )

                            Text(
                                text = mealItem.strInstructions ?: stringResource(Res.string.some_instructions),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = Dimensions.Small(isWide)),
                            )
                        }
                    }
                } else {
                    Column(
                        modifier =
                            modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
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
                            text = mealItem.strMeal ?: stringResource(Res.string.meal_here),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        Text(
                            text = mealItem.strInstructions ?: stringResource(Res.string.some_instructions),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
