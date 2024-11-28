package com.rutubishi.randomrecipes.data.repository

import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.MealCategory
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FakeMealRepository : IMealRepository {
    override fun fetchMealsByCategory(category: String): Flow<AppResource<MealsDto>> =
        flow {
            require(category.isNotBlank()) { "You require a valid category" }
            emit(AppResource.Loading())
            delay(100L)
            emit(AppResource.Success(MealsDto(meals = emptyList())))
        }.catch {
            emit(AppResource.Error(it))
        }

    override fun fetchMealDetails(mealId: String): Flow<AppResource<MealDto>> =
        flow {
            require(mealId.isNotBlank()) { "You require a valid ID" }
            emit(AppResource.Loading())
            delay(100L)
            emit(AppResource.Success(MealDto(meals = emptyList())))
        }.catch {
            emit(AppResource.Error(it))
        }

    override fun fetchMealCategories(): Flow<AppResource<CategoriesDto>> =
        flow {
            emit(AppResource.Loading())
            delay(100L)
            emit(
                AppResource.Success(
                    CategoriesDto(mealCategories = listOf(MealCategory("Breakfast"), MealCategory("Lunch"), MealCategory("Dinner"))),
                ),
            )
        }.catch {
            emit(AppResource.Error(it))
        }
}
