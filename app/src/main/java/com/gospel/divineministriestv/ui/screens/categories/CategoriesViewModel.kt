package com.gospel.divineministriestv.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gospel.divineministriestv.data.model.Playlist
import com.gospel.divineministriestv.data.repository.YouTubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiState: StateFlow<CategoriesUiState> = _uiState

    private val _selectedTab = MutableStateFlow(CategoryTab.TOPICS)
    val selectedTab: StateFlow<CategoryTab> = _selectedTab

    private var topicPlaylists: List<Playlist> = emptyList()
    private var yearPlaylists: List<Playlist> = emptyList()

    init {
        fetchCategories()
    }

    fun setTab(tab: CategoryTab) {
        _selectedTab.value = tab
        updateUiState()
    }

    fun fetchCategories() {
        viewModelScope.launch {
            _uiState.value = CategoriesUiState.Loading
            try {
                // Fetch Topic Playlists from YouTube
                topicPlaylists = repository.getPlaylists()
                
                // Generate Year Playlists dynamically
                yearPlaylists = generateYearPlaylists()
                
                updateUiState()
            } catch (e: Exception) {
                _uiState.value = CategoriesUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun updateUiState() {
        val currentList = if (_selectedTab.value == CategoryTab.TOPICS) topicPlaylists else yearPlaylists
        _uiState.value = CategoriesUiState.Success(currentList)
    }

    private fun generateYearPlaylists(): List<Playlist> {
        val currentYear = 2026 // As per requirement
        val startYear = 2018  // Ministry's early presence
        
        return (currentYear downTo startYear).map { year ->
            Playlist(
                id = "year_$year",
                title = "Sermons $year",
                thumbnailUrl = "https://img.youtube.com/vi/placeholder/hqdefault.jpg", // We'll handle this in UI or use a default logo
                videoCount = 0 // Will be determined when opened
            )
        }
    }
}

enum class CategoryTab {
    TOPICS, YEAR
}

sealed class CategoriesUiState {
    object Loading : CategoriesUiState()
    data class Success(val categories: List<Playlist>) : CategoriesUiState()
    data class Error(val message: String) : CategoriesUiState()
}
