package com.gospel.divineministriestv.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.data.repository.YouTubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LibraryUiState>(LibraryUiState.Loading)
    val uiState: StateFlow<LibraryUiState> = _uiState

    private val _selectedFilter = MutableStateFlow(LibraryFilter.ALL)
    val selectedFilter: StateFlow<LibraryFilter> = _selectedFilter

    private var allVideos: List<Video> = emptyList()
    private var currentPlaylistId: String? = null

    init {
        fetchVideos()
    }

    fun fetchVideos(playlistId: String? = null) {
        currentPlaylistId = playlistId
        viewModelScope.launch {
            _uiState.value = LibraryUiState.Loading
            try {
                allVideos = if (playlistId != null) {
                    repository.getPlaylistVideos(playlistId)
                } else {
                    repository.getLatestVideos()
                }
                applyFilter(_selectedFilter.value)
            } catch (e: Exception) {
                _uiState.value = LibraryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setFilter(filter: LibraryFilter) {
        _selectedFilter.value = filter
        applyFilter(filter)
    }

    private fun applyFilter(filter: LibraryFilter) {
        val filteredVideos = when (filter) {
            LibraryFilter.ALL -> allVideos
            LibraryFilter.YEAR -> allVideos.sortedByDescending { it.publishedAt }
            LibraryFilter.PROGRAM -> allVideos.sortedBy { it.title }
            LibraryFilter.TOPICS -> allVideos.sortedBy { video ->
                // Sort by videos that have more hashtags/topics in description
                video.description.count { it == '#' }
            }.reversed()
        }
        _uiState.value = LibraryUiState.Success(filteredVideos)
    }
}

enum class LibraryFilter {
    ALL, YEAR, PROGRAM, TOPICS
}

sealed class LibraryUiState {
    object Loading : LibraryUiState()
    data class Success(val videos: List<Video>) : LibraryUiState()
    data class Error(val message: String) : LibraryUiState()
}
