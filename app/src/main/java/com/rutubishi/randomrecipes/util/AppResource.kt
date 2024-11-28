package com.rutubishi.randomrecipes.util

sealed class AppResource<T> {
    data class Success<T>(
        val data: T,
    ) : AppResource<T>()

    data class Error<T>(
        val error: Throwable,
    ) : AppResource<T>()

    class Loading<T> : AppResource<T>()
}
