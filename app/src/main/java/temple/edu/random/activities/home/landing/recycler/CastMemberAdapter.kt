package temple.edu.random.activities.home.landing.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import temple.edu.random.activities.home.movies.CastMember
import temple.edu.random.activities.home.movies.CastMemberModel

class CastMemberAdapter(private val dataset: List<CastMemberModel>) :
    RecyclerView.Adapter<CastMemberAdapter.MovieExtendedViewHolder>() {

    class MovieExtendedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var castMember: CastMember

        init {
            (view as? CastMember)?.let {
                castMember = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieExtendedViewHolder {
        val view = CastMember(parent.context)
        return MovieExtendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieExtendedViewHolder, position: Int) =
        with(holder.castMember) {
            handleCastMemberUpdate(dataset[position])
        }

    override fun getItemCount(): Int = dataset.size
}