package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.rutubishi.randomrecipes.ui.isWideScreen
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import randomrecipes.composeapp.generated.resources.Res
import randomrecipes.composeapp.generated.resources.meal_here
import randomrecipes.composeapp.generated.resources.some_instructions
import randomrecipes.composeapp.generated.resources.something_went_wrong

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
                    Text(state.error.message ?: stringResource(Res.string.something_went_wrong))
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

                if(isWideScreen()){
                    IconButton(onClick = {
                        navController.popBackStack()
                    }, modifier = Modifier.align(Alignment.Start)) {
                        Icon(Icons.AutoMirrored.TwoTone.ArrowBack, contentDescription = null)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 100.dp, vertical = 8.dp)) {

                        AsyncImage(
                            model = mealItem.strMealThumb,
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .fillMaxWidth(0.5f)
                                    .fillMaxHeight(),
                            contentScale = ContentScale.Crop,
                        )

                        Column(
                            modifier = Modifier.padding(start = 16.dp),
                        ) {
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

                } else {
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


