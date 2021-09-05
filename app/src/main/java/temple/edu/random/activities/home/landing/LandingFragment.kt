package temple.edu.random.activities.home.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import temple.edu.random.activities.home.landing.recycler.MoviePreviewAdapter
import temple.edu.random.activities.home.movies.ExpandedMovie
import temple.edu.random.activities.home.movies.MoviePreview
import temple.edu.random.activities.home.movies.PreviewMovie
import temple.edu.random.databinding.FragmentLandingBinding
import temple.edu.random.globals.Global

class LandingFragment : Fragment(), MoviePreview.BottomModalListener {
    private lateinit var binding: FragmentLandingBinding
    private val movieManager by lazy { Global.application.movieManager }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(Global.application.movieManager) {
            super.onViewCreated(view, savedInstanceState)
            binding.landingNowPlayingRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.landingTopRatedRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.landingPopularRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.landingNowPlayingRecycler.adapter =
                MoviePreviewAdapter(nowPlayingMovies, this@LandingFragment)
            binding.landingTopRatedRecycler.adapter =
                MoviePreviewAdapter(topRatedMovies, this@LandingFragment)
            binding.landingPopularRecycler.adapter =
                MoviePreviewAdapter(popularMovies, this@LandingFragment)
        }

    override fun displayBottomModal(previewMovie: PreviewMovie) {
        val modalBottomSheet = ExpandedMovie(previewMovie)
        modalBottomSheet.show(childFragmentManager, TAG)
    }

    companion object {
        const val TAG = "ModalBottomSheet"

        fun newInstance() =
            LandingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}