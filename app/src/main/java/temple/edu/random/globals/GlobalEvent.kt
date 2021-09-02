package temple.edu.random.globals

import temple.edu.random.activities.home.movies.MoviePreview
import temple.edu.random.activities.home.movies.PreviewMovie

interface MovieEventListener {
    fun handleMovieUpdate(movie: PreviewMovie)
}