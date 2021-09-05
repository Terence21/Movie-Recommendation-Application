package temple.edu.random.activities.home.landing.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import temple.edu.random.activities.home.movies.CastMemberModel
import temple.edu.random.activities.home.movies.ExpandedMovieModel
import temple.edu.random.activities.home.movies.MoviePreview
import temple.edu.random.activities.home.movies.PreviewMovie

class MoviePreviewAdapter(
    private val dataset: List<PreviewMovie>,
    private val bottomModalListener: MoviePreview.BottomModalListener
) :
    RecyclerView.Adapter<MoviePreviewAdapter.MoviePreviewViewHolder>() {

    class MoviePreviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moviePreview: MoviePreview? = null

        init {
            (view as? MoviePreview)?.let {
                moviePreview = it
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePreviewViewHolder {
        val view = MoviePreview(parent.context)
        return MoviePreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePreviewViewHolder, position: Int) {
        holder.moviePreview?.let {
            it.handleMovieUpdate(dataset[position])
            it.setIconOnClickListener {
                bottomModalListener.displayBottomModal(ExpandedMovieModel( dataset[position], "director", "description", listOf<CastMemberModel>()))
            }
        }
    }

    override fun getItemCount(): Int = dataset.size
}