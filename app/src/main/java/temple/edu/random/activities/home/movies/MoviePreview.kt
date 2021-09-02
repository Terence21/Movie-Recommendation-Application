package temple.edu.random.activities.home.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import temple.edu.random.R
import temple.edu.random.databinding.MoviePreviewBinding
import temple.edu.random.globals.MovieEventListener

// have to implement listener for info icon
class MoviePreview(context: Context) : LinearLayout(context), View.OnClickListener,
    MovieEventListener {
    private val binding: MoviePreviewBinding
    private lateinit var iconListener: InfoIconFragment

    init {
        // attachToRoot set to false because used in onCreateViewHolder ?? or needs to be true.. test this
        // may cause error here
        LayoutInflater.from(context).inflate(R.layout.movie_preview, this, false)
        binding = MoviePreviewBinding.bind(this)
    }

    fun subscribeToInfoIcon(infoIconFragment: InfoIconFragment) {
        this.iconListener = infoIconFragment
    }

    private fun updateDirectorsText(text: String) {
        binding.moviePreviewDirectorTextView.text = text
    }

    private fun updateMovieTitle(text: String) {
        binding.moviePreviewTitleTextView.text = text
    }

    // update to emoji stars -> inclusive of half stars
    private fun updateRating(rating: Double) {
        binding.moviePreviewRatingTextView.text = rating.toString()
    }

    private fun updateImage(imageURL: String) {
        Picasso.with(context).load(imageURL).into(binding.moviePreviewImageView)
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

    override fun handleMovieUpdate(movie: PreviewMovie) = with(movie) {
        updateDirectorsText(director)
        updateMovieTitle(title)
        updateRating(rating)
        updateImage(imageURL)
    }


}