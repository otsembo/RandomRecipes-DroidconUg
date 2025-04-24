package com.rutubishi.randomrecipes.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MealsDto(
    val meals: List<Meal> = emptyList(),
)

@Serializable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
)
