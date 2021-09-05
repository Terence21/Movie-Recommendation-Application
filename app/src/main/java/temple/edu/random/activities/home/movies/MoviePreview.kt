package temple.edu.random.activities.home.movies

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import temple.edu.random.R
import temple.edu.random.databinding.MoviePreviewBinding
import temple.edu.random.globals.MovieEventListener

class MoviePreview(context: Context) : LinearLayout(context), View.OnClickListener,
    MovieEventListener {
    private val binding: MoviePreviewBinding
    private lateinit var iconListener: InfoIconFragment

    init {
        // attachToRoot set to false because merge layout
        LayoutInflater.from(context).inflate(R.layout.movie_preview, this, true)
        binding = MoviePreviewBinding.bind(this)

    }

    fun subscribeToInfoIcon(infoIconFragment: InfoIconFragment) {
        this.iconListener = infoIconFragment
    }

    fun setIconOnClickListener(onClickListener: View.OnClickListener) {
        binding.moviePreviewInfoImage.setOnClickListener(onClickListener)
    }

    private fun updateReleaseDateText(text: String) {
        // yyyy-mm-dd -> only showing year
        val year = text.split("-")[0]
        binding.moviePreviewReleaseTextView.text = year
    }

    private fun updateMovieTitle(text: String) {
        binding.moviePreviewTitleTextView.text = text
    }

    // update to emoji stars -> inclusive of half stars
    private fun updateRating(rating: Double) {
        binding.moviePreviewRatingTextView.text = "$rating ⭐"
    }

    private fun updateImage(imageURL: String) {
        Picasso.with(context).load("$BASE_IMAGE_URL$imageURL")
            .into(binding.moviePreviewImageView)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (id) {
                R.id.movie_preview_info_image -> {
                    Log.i("MOVIES", "onClick: Info Expand Clicked")
                    iconListener.launchInfoIcon()
                }
            }
        }
    }

    interface InfoIconFragment {
        fun launchInfoIcon()
    }

    interface BottomModalListener {
        fun displayBottomModal(previewMovie: PreviewMovie)
    }

    override fun handleMovieUpdate(movie: PreviewMovie) = with(movie) {
        updateReleaseDateText(releaseDate)
        updateMovieTitle(title)
        updateRating(rating)
        updateImage(imageURL)
    }

    private companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
    }
}