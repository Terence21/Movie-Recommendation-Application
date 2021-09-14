package temple.edu.random.activities.home.landing.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import temple.edu.random.activities.home.movies.CurrentMoviePreview
import temple.edu.random.activities.home.movies.PreviewMovie

class MoviePreviewAdapter(
    private var dataset: List<PreviewMovie>,
    private val bottomModalListener: CurrentMoviePreview.BottomModalListener
) :
    RecyclerView.Adapter<MoviePreviewAdapter.MoviePreviewViewHolder>() {

    class MoviePreviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moviePreview: CurrentMoviePreview? = null

        init {
            (view as? CurrentMoviePreview)?.let {
                moviePreview = it
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePreviewViewHolder {
        val view = CurrentMoviePreview(parent.context)
        return MoviePreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePreviewViewHolder, position: Int) {
        holder.moviePreview?.let {
            it.handleMovieUpdate(dataset[position])
            it.setIconOnClickListener {
                bottomModalListener.displayBottomModal(dataset[position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapDataSet(dataSet: List<PreviewMovie>) {
        Log.i("MoviePreviewAdapter", "swapDataSet: updating data")
        dataset = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size
}