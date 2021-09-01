package temple.edu.random.activities.home.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import temple.edu.random.R
import temple.edu.random.databinding.MoviePreviewBinding

// hav eto implement listener for info icon
class MoviePreview(context: Context) : LinearLayout(context), View.OnClickListener {
    private val binding: MoviePreviewBinding
    private lateinit var iconListener: InfoIconFragment

    init {
        LayoutInflater.from(context).inflate(R.layout.movie_preview, this, true)
        binding = MoviePreviewBinding.bind(this)
    }

    fun subscribeToInfoIcon(infoIconFragment: InfoIconFragment) {
        this.iconListener = infoIconFragment
    }

    fun updateDirectorsText(text: String) {
        binding.moviePreviewDirectorTextView.text = text
    }

    fun updateMovieTitle(text: String) {
        binding.moviePreviewTitleTextView.text = text
    }

    // update to emoji stars -> inclusive of half stars
    fun updateRating(text: String) {
        binding.moviePreviewRatingTextView.text = text
    }

    override fun onClick(v: View?) {
        v?.let {
            when (id) {
                R.id.movie_preview_info_image -> {
                    iconListener.launchInfoIcon()
                }
            }
        }
    }

    interface InfoIconFragment {
        fun launchInfoIcon()
    }


}