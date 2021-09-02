package temple.edu.random.globals

import temple.edu.random.activities.home.movies.PreviewMovie

class MovieManager : EventManager<MovieEventListener>() {
    private val movieListeners by lazy { mutableListOf<MovieEventListener>() }
    lateinit var currentPreviewMovie: PreviewMovie

    override fun subscribeToEvent(subscriber: MovieEventListener) {
        movieListeners.add(subscriber)
    }

    override fun unsubscribeToEvent(unsubscriber: MovieEventListener) {
        movieListeners.remove(unsubscriber)
    }

    fun updateCurrentPreviewMovie(movie: PreviewMovie) {
        currentPreviewMovie = movie
        movieListeners.forEach { it.handleMovieUpdate(movie) }
    }
}