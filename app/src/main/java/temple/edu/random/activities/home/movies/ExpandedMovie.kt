package temple.edu.random.activities.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import temple.edu.random.R
import temple.edu.random.databinding.ExpandedMovieBinding

class ExpandedMovie(previewMovie: PreviewMovie) : BottomSheetDialogFragment() {

    private lateinit var binding: ExpandedMovieBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expanded_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ExpandedMovieBinding.bind(view)
    }

}