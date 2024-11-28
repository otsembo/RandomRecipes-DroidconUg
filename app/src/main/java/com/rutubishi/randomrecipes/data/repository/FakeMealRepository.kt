package com.rutubishi.randomrecipes.data.repository

import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.Meal
import com.rutubishi.randomrecipes.data.model.MealCategory
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FakeMealRepository : IMealRepository {
    val sampleMeals =
        listOf(
            Meal(
                idMeal = "52771",
                strMeal = "Spicy Arrabiata Penne",
                strMealThumb = "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
            ),
            Meal(
                idMeal = "52977",
                strMeal = "Corba",
                strMealThumb = "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg",
            ),
            Meal(
                idMeal = "53060",
                strMeal = "Burek",
                strMealThumb = "https://www.themealdb.com/images/media/meals/tkxquw1628771028.jpg",
            ),
            Meal(
                idMeal = "52772",
                strMeal = "Teriyaki Chicken Casserole",
                strMealThumb = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
            ),
            Meal(
                idMeal = "52893",
                strMeal = "Chicken Karaage",
                strMealThumb = "https://www.themealdb.com/images/media/meals/tyywsw1504366327.jpg",
            ),
        )

    override fun fetchMealsByCategory(category: String): Flow<AppResource<MealsDto>> =
        flow {
            emit(AppResource.Loading())
            require(category.isNotBlank()) { "You require a valid category" }
            delay(100L)
            emit(AppResource.Success(MealsDto(meals = sampleMeals)))
        }.catch {
            emit(AppResource.Error(it))
        }

    override fun fetchMealDetails(mealId: String): Flow<AppResource<MealDto>> =
        flow {
            emit(AppResource.Loading())
            require(mealId.isNotBlank()) { "You require a valid ID" }
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
