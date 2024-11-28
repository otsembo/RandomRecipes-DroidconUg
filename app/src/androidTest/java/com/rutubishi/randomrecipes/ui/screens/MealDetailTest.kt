package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealItem
import com.rutubishi.randomrecipes.data.repository.FakeMealRepository
import com.rutubishi.randomrecipes.util.AppResource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealDetailTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mealDetail_showLoading_whenStateIsLoading() {
        // Setup
        val uiState = MealDetailScreenUiState(appState = AppResource.Loading())
        val mockRepo = FakeMealRepository()
        val mockVM = MealDetailVM(mockRepo)

        // Set content
        composeTestRule.setContent {
            MealDetail(route = MealDetailRoute("123"), uiState = uiState, vm = mockVM)
        }

        // Assertions
        composeTestRule.onNodeWithTag("progress_indicator").assertIsDisplayed() // Assuming you tagged the CircularProgressIndicator
    }

    @Test
    fun mealDetail_showError_whenStateIsError() {
        // Setup
        val errorMessage = "You require a valid ID"
        val uiState = MealDetailScreenUiState(appState = AppResource.Error(Exception(errorMessage)))
        val mockRepo = FakeMealRepository()
        val mockVM = MealDetailVM(mockRepo)

        // Set content
        composeTestRule.setContent {
            MealDetail(route = MealDetailRoute(""), uiState = uiState, vm = mockVM)
        }

        // Assertions
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun mealDetail_showMealDetails_whenStateIsSuccess() {
        // Setup
        val mealItem = MealItem(strMeal = "Test Meal", strInstructions = "Test Instructions")
        val uiState = MealDetailScreenUiState(appState = AppResource.Success(MealDto(listOf(mealItem))))
        val mockRepo = FakeMealRepository()
        val mockVM = MealDetailVM(mockRepo)

        // Set content
        composeTestRule.setContent {
            MealDetail(route = MealDetailRoute("123"), uiState = uiState, vm = mockVM)
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Meal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Instructions").assertIsDisplayed()
    }
}
