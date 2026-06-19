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

    init {
        fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _uiState.value = LibraryUiState.Loading
            try {
                val videos = repository.getLatestVideos()
                _uiState.value = LibraryUiState.Success(videos)
            } catch (e: Exception) {
                _uiState.value = LibraryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class LibraryUiState {
    object Loading : LibraryUiState()
    data class Success(val videos: List<Video>) : LibraryUiState()
    data class Error(val message: String) : LibraryUiState()
}
