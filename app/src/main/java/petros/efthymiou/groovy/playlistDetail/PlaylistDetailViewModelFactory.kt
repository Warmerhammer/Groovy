package petros.efthymiou.groovy.playlistDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.playlist.PlaylistRepository
import javax.inject.Inject

class PlaylistDetailViewModelFactory @Inject constructor(
    private val service: PlaylistDetailService
) :
    ViewModelProvider.Factory {

        override fun<T: ViewModel?> create(modelClass: Class<T>) : T {
            return PlaylistDetailViewModel(service) as T
        }

}