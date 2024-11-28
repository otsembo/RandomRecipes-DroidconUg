package com.rutubishi.randomrecipes.data.repository

import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MealRepositoryTest {
    @Test
    fun fetchMealsByCategory_success() =
        runBlocking {
            val fakeRepository = FakeMealRepository()

            val flow = fakeRepository.fetchMealsByCategory("Seafood")
            val result = flow.first() // Get the first emitted value

            assertTrue(result is AppResource.Loading) // First emission should be Loading
            val successResult = flow.take(2).last() // Get the second emitted value (Success)
            assertTrue(successResult is AppResource.Success)
            assertEquals(fakeRepository.sampleMeals, (successResult as AppResource.Success).data.meals)
        }

    @Test
    fun fetchMealsByCategory_error() =
        runBlocking {
            val fakeRepository = FakeMealRepository()

            val flow = fakeRepository.fetchMealsByCategory("")
            val result = flow.first() // Get the first emitted value

            assertTrue(result is AppResource.Loading) // First emission should be Loading
            val errorResult = flow.take(2).last() // Get the second emitted value (Error)
            assertTrue(errorResult is AppResource.Error)
            assertTrue((errorResult as AppResource.Error).error.message!!.contains("You require a valid category"))
        }
}
