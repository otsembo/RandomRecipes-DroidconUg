package com.rutubishi.randomrecipes.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rutubishi.randomrecipes.data.model.MealDto
import com.rutubishi.randomrecipes.data.model.MealItem
import com.rutubishi.randomrecipes.data.repository.IMealRepository
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailVM(
    private val repo: IMealRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MealDetailScreenUiState> = MutableStateFlow(MealDetailScreenUiState())
    val uiState: StateFlow<MealDetailScreenUiState> = _uiState

    fun fetchMealById(id: String) {
        viewModelScope.launch {
            repo.fetchMealDetails(id).collectLatest { state ->
                _uiState.update { it.copy(appState = state) }
                when (state) {
                    is AppResource.Error -> _uiState.update { it.copy(meal = null) }
                    is AppResource.Loading -> _uiState.update { it.copy(meal = null) }
                    is AppResource.Success -> _uiState.update { it.copy(meal = state.data.meals.firstOrNull()) }
                }
            }
        }
    }
}

data class MealDetailScreenUiState(
    val meal: MealItem? = null,
    val appState: AppResource<MealDto> = AppResource.Loading(),
)
