package com.rutubishi.randomrecipes.helpers

import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.data.repository.IMealRepository
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object TestHelpers {
    object FakeMealRepository : IMealRepository {
        override fun fetchMealsByCategory(category: String): Flow<AppResource<MealsDto>> =
            flow {
                emit(AppResource.Success(MealsDto(meals = listOf())))
            }

        override fun fetchMealDetails(mealId: String): Flow<AppResource<MealDto>> {
            TODO("Not yet implemented")
        }

        override fun fetchMealCategories(): Flow<AppResource<CategoriesDto>> {
            TODO("Not yet implemented")
        }
    }
}
