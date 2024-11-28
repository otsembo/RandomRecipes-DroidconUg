package com.rutubishi.randomrecipes.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.rutubishi.randomrecipes.data.model.CategoriesDto
import com.rutubishi.randomrecipes.data.model.MealCategory
import com.rutubishi.randomrecipes.data.repository.IMealRepository
import com.rutubishi.randomrecipes.util.AppResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginVM(
    private val mealRepository: IMealRepository,
) : ViewModel() {
    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    init {
        viewModelScope.launch {
            mealRepository
                .fetchMealCategories()
                .collectLatest { cat ->
                    _loginUiState.update { it.copy(appState = cat) }
                    when (cat) {
                        is AppResource.Error -> _loginUiState.update { it.copy(error = cat.error.message) }
                        is AppResource.Loading -> _loginUiState.update { it.copy(error = null) }
                        is AppResource.Success -> {
                            _loginUiState.update {
                                it.copy(
                                    selectedCategory = fetchRandomCategory(cat.data.mealCategories),
                                    error = null,
                                )
                            }
                        }
                    }
                }
        }
    }

    fun updateUsername(username: String) {
        _loginUiState.update { it.copy(username = username) }
    }

    fun login(navHostController: NavHostController) {
        val username = (_loginUiState.value.username?.length ?: 0)
        if (username >= 4) {
            navHostController.navigate(MealsRoute(_loginUiState.value.username!!, _loginUiState.value.selectedCategory!!))
        } else {
            _loginUiState.update { it.copy(error = "Your username is too short.") }
        }
    }

    private fun fetchRandomCategory(mealCategories: List<MealCategory>): String =
        mealCategories
            .random()
            .strCategory
}

data class LoginUiState(
    val username: String? = null,
    val selectedCategory: String? = null,
    val appState: AppResource<CategoriesDto>? = null,
    val error: String? = null,
)
