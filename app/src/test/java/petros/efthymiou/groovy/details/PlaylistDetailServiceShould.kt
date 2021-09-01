package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.playlistDetail.PlaylistDetail
import petros.efthymiou.groovy.playlistDetail.PlaylistDetailAPI
import petros.efthymiou.groovy.playlistDetail.PlaylistDetailService
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistDetailServiceShould : BaseUnitTest() {
    /*
    1. Mock PlaylistDetailsAPI
    2. fetchPlaylistDetailsFromAPI()
        verify that we are using the mockAPI object to make the HTTP call and fetch the details from our backend.
     3. convertValuesToFlowResultAndEmitThem()
            verify that the playlistDetails object we are receiving from API is wrapped inside the Kotlin result
            and then emitted in Kotlin flow
      4. emitErrorResultWhenNetworkFails()
            whenever we fail to fetch the playlist from backend - the service is translating the error to a more user friendly one
            is wrapping it within the kotlin flow and emitting it.
       5. Refactor production code and unit test code
       6. Run Acceptance test
     */
    private lateinit var service: PlaylistDetailService
    private val api: PlaylistDetailAPI = mock()
    private val id = "100"
    private val playlistDetail: PlaylistDetail = mock()
    private val exception = RuntimeException("Damn backend developers")

    @Test
    fun fetchPlaylistDetailsFromAPI(): Unit = runBlockingTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem(): Unit = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetail), service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails(): Unit = runBlockingTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message
        )

    }

    private fun mockErrorCase() {
        runBlocking{
            whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)
        }


        service = PlaylistDetailService(api)
    }

    private fun mockSuccessfulCase() {
        runBlocking{
            whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetail)
        }

        service = PlaylistDetailService(api)
    }


}