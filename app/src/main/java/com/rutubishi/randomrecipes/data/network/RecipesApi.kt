package com.rutubishi.randomrecipes.data.network

import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.util.AppResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

private val recipesApiClient =
    HttpClient(OkHttp) {
        expectSuccess = true
        engine {
            config {
                callTimeout(15L, TimeUnit.SECONDS)
            }
        }
        defaultRequest {
            url("https://www.themealdb.com/api/json/v1/1/")
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }
    }

suspend fun getMealCategories(): AppResource<CategoriesDto> =
    try {
        AppResource.Success(
            data = recipesApiClient.get("list.php?c=list").body(),
        )
    } catch (e: Exception) {
        AppResource.Error(e)
    }

suspend fun getMealsByCategory(category: String): AppResource<MealsDto> =
    try {
        AppResource.Success(
            data = recipesApiClient.get("filter.php?c=$category").body(),
        )
    } catch (e: Exception) {
        AppResource.Error(e)
    }

suspend fun getMealDetails(mealId: String): AppResource<MealDto> =
    try {
        AppResource.Success(
            data = recipesApiClient.get("lookup.php?i=$mealId").body(),
        )
    } catch (e: Exception) {
        AppResource.Error(e)
    }
