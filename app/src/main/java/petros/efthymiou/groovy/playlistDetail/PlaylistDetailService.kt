package petros.efthymiou.groovy.playlistDetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val api: PlaylistDetailAPI
) {

// suspend fun fetchPlaylistDetails(id: String) : Flow<Result<PlaylistDetail>>{
//       return flow {
//           api.fetchPlaylistDetails(id)
//           emit(Result.success(PlaylistDetail("", "", "")))
//       }
//    }

  suspend fun fetchPlaylistDetails(id: String) : Flow<Result<PlaylistDetail>> {
        return flow {
            emit(Result.success(api.fetchPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }

    }

}
