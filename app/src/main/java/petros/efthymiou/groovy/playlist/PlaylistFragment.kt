package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.view.*
import petros.efthymiou.groovy.R
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        observeLoader()

        observeLiveData(view)

        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(
            this as LifecycleOwner, { loading ->
                when (loading) {
                    true -> loader.visibility = View.VISIBLE
                    else -> loader.visibility = View.GONE
                }
            })
    }

    private fun observeLiveData(view: View) {
        viewModel.playlists.observe(
            this as LifecycleOwner,
            { playlists ->
                if (playlists.getOrNull() != null)
                    setUpList(view.playlists_list, playlists.getOrNull()!!)
                else {
                    Log.d("PlayListFragment", "Playlist :: $playlists")
                }
            })
    }


    private fun setUpList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            Log.e("PlaylistFragment", "view :: $view")
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                val action: NavDirections =
                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(id)

                findNavController().navigate(action)
            }
            Log.v("PlaylistFragment", "view :: $view")

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlaylistFragment =
            PlaylistFragment().apply {}
    }

}