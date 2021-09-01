package petros.efthymiou.groovy.playlist

import com.jakewharton.espresso.OkHttp3IdlingResource
import androidx.test.espresso.IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)


@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playListAPI(retrofit: Retrofit): PlaylistAPI = retrofit.create(PlaylistAPI::class.java)


    @Provides()
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.86.60:2999/") //please check that it matches current local ip address
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}