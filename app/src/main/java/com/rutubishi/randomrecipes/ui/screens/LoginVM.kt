package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.repository.MealRepository
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginVM(
    private val mealRepository: MealRepository,
) : ViewModel() {
    private val _categories: MutableStateFlow<AppResource<CategoriesDto>> = MutableStateFlow(AppResource.Loading())
    val categories: StateFlow<AppResource<CategoriesDto>> = _categories

    private val _category: MutableState<String?> = mutableStateOf(null)
    val category: State<String?> get() = _category

    private val _username: MutableState<String?> = mutableStateOf(null)
    val username: State<String?> get() = _username

    init {
        viewModelScope.launch {
            mealRepository.fetchMealCategories().collectLatest {
                _categories.value = it
                if (it is AppResource.Success) {
                    fetchRandomCategory()
                }
            }
        }
    }

    fun login(username: String): Boolean = username.length >= 4

    private fun fetchRandomCategory() {
        val cat =
            (_categories.value as AppResource.Success<CategoriesDto>)
                .data.mealCategories
                .random()
                .strCategory
        _category.value = cat
    }
}
