package com.gospel.divineministriestv.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gospel.divineministriestv.data.model.Playlist
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.data.repository.YouTubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val latestVideos = repository.getLatestVideos()
                val liveVideo = repository.checkLiveStatus()
                val playlists = repository.getPlaylists()
                _uiState.value = HomeUiState.Success(
                    latestVideos = latestVideos,
                    liveVideo = liveVideo,
                    playlists = playlists
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val latestVideos: List<Video>,
        val liveVideo: Video?,
        val playlists: List<Playlist>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
