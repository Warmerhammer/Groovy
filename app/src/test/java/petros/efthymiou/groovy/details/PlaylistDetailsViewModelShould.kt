package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.playlistDetail.PlaylistDetail
import petros.efthymiou.groovy.playlistDetail.PlaylistDetailService
import petros.efthymiou.groovy.playlistDetail.PlaylistDetailViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould : BaseUnitTest() {
    private lateinit var viewModel: PlaylistDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailService = mock()
    private val playlistDetail: PlaylistDetail = mock()
    private val expected = Result.success(playlistDetail)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetail>(exception)

    @Test
    fun getPlaylistDetailsFromServiceIsCalled(): Unit = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)

    }

    @Test
    fun emitPlaylistDetailsFromService(): Unit = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails(): Unit = runBlockingTest {
        mockErrorCase()
        viewModel.getPlaylistDetails(id)

        // assert that the error our service is throwing is the error that our
        // live data is emitting
        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showSpinnerWhileLoading(): Unit = runBlockingTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoad() : Unit = runBlockingTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel = PlaylistDetailViewModel(service)
    }

    private suspend fun mockErrorCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )

        viewModel = PlaylistDetailViewModel(service)
    }


}