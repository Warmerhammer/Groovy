package petros.efthymiou.groovy.playlistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class PlaylistDetailViewModel(
    private val service: PlaylistDetailService
) : ViewModel() {
    val playlistDetails: MutableLiveData<Result<PlaylistDetail>> = MutableLiveData()

    val loader = MutableLiveData<Boolean>()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.fetchPlaylistDetails(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }


}