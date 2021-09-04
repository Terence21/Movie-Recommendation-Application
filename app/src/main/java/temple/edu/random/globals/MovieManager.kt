package temple.edu.random.globals

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import temple.edu.random.activities.home.movies.PreviewMovie
import temple.edu.random.globals.MovieManager.MOVIES_TYPE.*

class MovieManager : EventManager<MovieEventListener>() {

    private val movieListeners by lazy { mutableListOf<MovieEventListener>() }
    private val client by lazy { OkHttpClient() }
    val nowPlayingMovies by lazy { mutableListOf<PreviewMovie>() }
    val topRatedMovies by lazy { mutableListOf<PreviewMovie>() }
    val popularMovies by lazy { mutableListOf<PreviewMovie>() }

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

    fun initializeLandingMovies() {
        initializeNowPlayingMovies()
        initializeTopRatedMovies()
        initializePopularMovies()
    }

    private fun initializeNowPlayingMovies() {
        val url = "$BASE_MOVIE_URL${NOW_PLAYING.param}$PAGE1"
        requestLandingPreviewMoviesList(url, NOW_PLAYING)
    }

    private fun initializeTopRatedMovies() {
        val url = "$BASE_MOVIE_URL${TOP_RATED.param}$PAGE1"
        requestLandingPreviewMoviesList(url, TOP_RATED)
    }

    private fun initializePopularMovies() {
        val url = "$BASE_MOVIE_URL${POPULAR.param}$PAGE1"
        requestLandingPreviewMoviesList(url, POPULAR)
    }

    /**
     * For endpoints that directly extend movies/
     * Endpoints must have the following attributes: results -> title, vote_average, poster_path
     *
     */
    private fun requestLandingPreviewMoviesList(url: String, type: MOVIES_TYPE) {
        val request = Request.Builder()
            .url(url)
            .addHeader(
                AUTHORIZATION,
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NmU1NTRhMzBhMzRkNGJlY2FmYjZmNGY3Y2ZkNzZmYyIsInN1YiI6IjYxMmU2NzRjMjIzZTIwMDAyZmRlMDgxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.O4SmPGWsWkF-6nr_AtEZ_DRDwCd9tvkR5V7DSC94Zoo"
            )
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .build()

        val response = client.newCall(request).execute()
        val previewMovies = mutableListOf<PreviewMovie>()
        // get respective response map { .. , .. , ..} => json starts as map/dict
        var jsonMap: Map<String, Any> = HashMap<String, Any>()
        val gson = Gson()
        Log.i("MOVIES", "json: $response ")

        response.body?.let {
            jsonMap = gson.fromJson(
                suspendResponseBodyOutput(response),
                jsonMap.javaClass
            ) as Map<String, Any>

            // results is an array of objects => response : [ { .. }, { .. },]
            val results = jsonMap["results"] as ArrayList<*>

            // convert each result object into a Preview Movie
            results.forEach { resultsIndex ->
                val entry = resultsIndex as? Map<*, *>
                entry?.let {
                    val movie = Global.tryNotErrorExpression {
                        val mov = PreviewMovie(
                            it["title"] as String,
                            "",
                            it["vote_average"] as Double,
                            it["poster_path"] as String
                        )
                        mov
                    }
                    movie?.let { previewMovies.add(movie) }
                }
            }
        }
        setMovieType(type, previewMovies)

    }

    private fun setMovieType(type: MOVIES_TYPE, previewMovies: MutableList<PreviewMovie>) =
        when (type) {
            NOW_PLAYING -> {
                nowPlayingMovies.clear()
                nowPlayingMovies.addAll(previewMovies)
            }
            TOP_RATED -> {
                topRatedMovies.clear()
                topRatedMovies.addAll(previewMovies)
            }
            POPULAR -> {
                popularMovies.clear()
                popularMovies.addAll(previewMovies)
            }
        }

    private fun suspendResponseBodyOutput(response: Response): String? = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext response.body?.string()
        }
    }

    private enum class MOVIES_TYPE(val param: String) {
        NOW_PLAYING(NOW_PLAYING_PARAM), TOP_RATED(TOP_RATED_PARAM), POPULAR(POPULAR_PARAM)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val CONTENT_TYPE = "Accept"
        private const val CONTENT_TYPE_VALUE = "application/json"
        private const val BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/"
        private const val NOW_PLAYING_PARAM = "now_playing"
        private const val TOP_RATED_PARAM = "top_rated"
        private const val POPULAR_PARAM = "popular"
        private const val PAGE1 = "?page=1"
    }
}