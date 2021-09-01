package petros.efthymiou.groovy.playlistDetail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PlaylistDetailModule {

    @Provides
    fun playlistDetailsAPI(retrofit: Retrofit): PlaylistDetailAPI =
        retrofit.create(PlaylistDetailAPI::class.java)

}