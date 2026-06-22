package com.gospel.divineministriestv.ui.screens.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gospel.divineministriestv.data.model.Comment
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.data.repository.YouTubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Loading)
    val uiState: StateFlow<VideoDetailUiState> = _uiState

    private var videoId: String? = null

    fun fetchVideoDetails(videoId: String) {
        this.videoId = videoId
        viewModelScope.launch {
            _uiState.value = VideoDetailUiState.Loading
            
            try {
                val videos = repository.getVideoDetails(videoId)
                val video = videos.firstOrNull()
                
                if (video != null) {
                    val comments = try {
                        repository.getVideoComments(videoId)
                    } catch (e: Exception) {
                        emptyList()
                    }

                    repository.isFavorite(videoId).collect { isFav ->
                        _uiState.value = VideoDetailUiState.Success(
                            video = video,
                            isFavorite = isFav,
                            comments = comments
                        )
                    }
                } else {
                    _uiState.value = VideoDetailUiState.Error("Video not found")
                }
            } catch (e: Exception) {
                _uiState.value = VideoDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is VideoDetailUiState.Success) {
            viewModelScope.launch {
                repository.toggleFavorite(currentState.video, currentState.isFavorite)
            }
        }
    }
}

sealed class VideoDetailUiState {
    object Loading : VideoDetailUiState()
    data class Success(
        val video: Video,
        val isFavorite: Boolean,
        val comments: List<Comment> = emptyList()
    ) : VideoDetailUiState()
    data class Error(val message: String) : VideoDetailUiState()
}
