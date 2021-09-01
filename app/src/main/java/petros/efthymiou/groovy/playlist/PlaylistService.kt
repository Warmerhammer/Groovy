package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.playlistDetail.PlaylistDetail
import petros.efthymiou.groovy.playlistDetail.PlaylistDetailAPI
import javax.inject.Inject
import kotlin.RuntimeException

class PlaylistService @Inject constructor(
    private val api: PlaylistAPI,
) {

    suspend fun fetchPlaylists() : Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
