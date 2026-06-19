package com.gospel.divineministriestv.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gospel.divineministriestv.data.model.Playlist
import com.gospel.divineministriestv.data.repository.YouTubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiState: StateFlow<CategoriesUiState> = _uiState

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        viewModelScope.launch {
            _uiState.value = CategoriesUiState.Loading
            try {
                val playlists = repository.getPlaylists()
                _uiState.value = CategoriesUiState.Success(playlists)
            } catch (e: Exception) {
                _uiState.value = CategoriesUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class CategoriesUiState {
    object Loading : CategoriesUiState()
    data class Success(val categories: List<Playlist>) : CategoriesUiState()
    data class Error(val message: String) : CategoriesUiState()
}
