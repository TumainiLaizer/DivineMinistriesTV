package com.gospel.divineministriestv.ui.screens.video

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
class VideoDetailViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Loading)
    val uiState: StateFlow<VideoDetailUiState> = _uiState

    fun fetchVideoDetails(videoId: String) {
        viewModelScope.launch {
            _uiState.value = VideoDetailUiState.Loading
            try {
                val videos = repository.getVideoDetails(videoId)
                val video = videos.firstOrNull()
                if (video != null) {
                    _uiState.value = VideoDetailUiState.Success(video)
                } else {
                    _uiState.value = VideoDetailUiState.Error("Video not found")
                }
            } catch (e: Exception) {
                _uiState.value = VideoDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class VideoDetailUiState {
    object Loading : VideoDetailUiState()
    data class Success(val video: Video) : VideoDetailUiState()
    data class Error(val message: String) : VideoDetailUiState()
}
