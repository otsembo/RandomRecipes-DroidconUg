package com.rutubishi.randomrecipes.data.repository

import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.data.network.getMealCategories
import com.rutubishi.randomrecipes.data.network.getMealDetails
import com.rutubishi.randomrecipes.data.network.getMealsByCategory
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MealRepository {
    fun fetchMealsByCategory(category: String): Flow<AppResource<MealsDto>> =
        flow {
            emit(AppResource.Loading())
            val response = getMealsByCategory(category)
            emit(response)
        }.catch {
            emit(AppResource.Error(it))
        }

    fun fetchMealDetails(mealId: String): Flow<AppResource<MealsDto>> =
        flow {
            emit(AppResource.Loading())
            val response = getMealDetails(mealId)
            emit(response)
        }.catch {
            emit(AppResource.Error(it))
        }

    fun fetchMealCategories(): Flow<AppResource<CategoriesDto>> =
        flow {
            emit(AppResource.Loading())
            val response = getMealCategories()
            emit(response)
        }.catch {
            emit(AppResource.Error(it))
        }
}
