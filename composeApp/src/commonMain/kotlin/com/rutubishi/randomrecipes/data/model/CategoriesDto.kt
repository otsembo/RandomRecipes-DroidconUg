package com.rutubishi.randomrecipes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDto(
    @SerialName("meals")
    val mealCategories: List<MealCategory>,
)

@Serializable
data class MealCategory(
    val strCategory: String,
)
