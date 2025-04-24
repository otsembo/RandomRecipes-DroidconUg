package com.rutubishi.randomrecipes.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rutubishi.randomrecipes.data.model.Meal
import com.rutubishi.randomrecipes.data.model.MealsDto
import com.rutubishi.randomrecipes.data.repository.IMealRepository
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealsVM(
    private val repo: IMealRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MealsScreenUiState> = MutableStateFlow(MealsScreenUiState())
    val uiState: StateFlow<MealsScreenUiState> = _uiState

    fun fetchMealsByCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        viewModelScope.launch {
            repo.fetchMealsByCategory(_uiState.value.selectedCategory).collectLatest { state ->
                _uiState.update { it.copy(appState = state) }
                when (state) {
                    is AppResource.Error -> _uiState.update { it.copy(meals = emptyList()) }
                    is AppResource.Loading -> _uiState.update { it.copy(meals = emptyList()) }
                    is AppResource.Success -> _uiState.update { it.copy(meals = state.data.meals) }
                }
            }
        }
    }

    fun refreshMeals() {
        viewModelScope.launch {
            _uiState.update { it.copy(appState = AppResource.Loading()) }
            repo.fetchMealCategories().collectLatest { categoriesState ->
                when (categoriesState) {
                    is AppResource.Error -> _uiState.update { it.copy(meals = emptyList()) }
                    is AppResource.Loading -> _uiState.update { it.copy(meals = emptyList()) }
                    is AppResource.Success -> {
                        val category = categoriesState.data.mealCategories.random()
                        fetchMealsByCategory(category.strCategory)
                    }
                }
            }
        }
    }
}

data class MealsScreenUiState(
    val selectedCategory: String = "Pasta",
    val meals: List<Meal> = emptyList(),
    val appState: AppResource<MealsDto> = AppResource.Loading(),
)
