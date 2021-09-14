package temple.edu.random.globals

import temple.edu.random.activities.home.movies.PreviewMovie

interface MovieEventListener
interface CurrentMovieUpdateListener : MovieEventListener {
    fun handleMovieUpdate(previewMovie: PreviewMovie)
}

interface RecentMovieListener : MovieEventListener {
    fun handleRecentMovies(recentMovies: Set<PreviewMovie>)
}