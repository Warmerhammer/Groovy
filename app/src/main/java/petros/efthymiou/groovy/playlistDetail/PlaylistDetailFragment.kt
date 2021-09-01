package petros.efthymiou.groovy.playlistDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import petros.efthymiou.groovy.R
import javax.inject.Inject


@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    val args: PlaylistDetailFragmentArgs by navArgs()

    lateinit var viewModel: PlaylistDetailViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id: String = args.playlistId

        setupViewModel()

        observeLoader()

        viewModel.getPlaylistDetails(id)
        observePlaylistDetails()

        return view
    }

    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(
            this as LifecycleOwner,
            { playlistDetails ->
                if (playlistDetails.getOrNull() != null) {
                    setupUI(playlistDetails)
                } else {
                    Snackbar.make(
                        playlist_details_root,
                        R.string.generic_error,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun observeLoader() {
        viewModel.loader.observe(
            this as LifecycleOwner,
            { loading ->
                when (loading) {
                    true -> detail_loader.visibility = View.VISIBLE
                    else -> detail_loader.visibility = View.GONE
                }
            })

    }

    private fun setupUI(
        playlistDetails: Result<PlaylistDetail>
    ) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details
    }


    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistDetailViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlaylistDetailFragment =
            PlaylistDetailFragment()
    }

}