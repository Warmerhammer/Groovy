package petros.efthymiou.groovy.playlistDetail

import retrofit2.http.GET
import retrofit2.http.Path


interface PlaylistDetailAPI {

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetail

}